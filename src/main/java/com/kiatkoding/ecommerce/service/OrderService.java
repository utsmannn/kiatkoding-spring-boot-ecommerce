package com.kiatkoding.ecommerce.service;

import com.kiatkoding.ecommerce.component.ApplicationUtils;
import com.kiatkoding.ecommerce.component.JwtUtils;
import com.kiatkoding.ecommerce.model.CustomUserDetails;
import com.kiatkoding.ecommerce.model.entity.*;
import com.kiatkoding.ecommerce.model.response.PagingInfo;
import com.kiatkoding.ecommerce.model.template.InvoiceRequest;
import com.kiatkoding.ecommerce.model.template.InvoiceResponse;
import com.kiatkoding.ecommerce.model.template.InvoiceWebhookRequest;
import com.kiatkoding.ecommerce.repository.CartRepository;
import com.kiatkoding.ecommerce.repository.OrderProductRepository;
import com.kiatkoding.ecommerce.repository.OrderRepository;
import com.kiatkoding.ecommerce.repository.OrderResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderResultRepository orderResultRepository;
    private final CartRepository cartRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final InvoiceService invoiceService;
    private final ApplicationUtils applicationUtils;

    @Value("${xendit.webhook.secret}")
    private String xenditWebhookKey;

    public PagingInfo<OrderEntity> getOrders(int page, int size) {
        int userId = jwtUtils.getUserId();
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Pageable pageable = pageRequest.withSort(Sort.by("createdAt").descending());
        Page<OrderEntity> orderEntityPage = orderRepository.findAllByUserId(
                userId, pageable
        );

        return PagingInfo.convertFromPage(orderEntityPage);
    }

    public OrderEntity createOrder(String idsAsString) {
        int userId = jwtUtils.getUserId();

        List<Integer> ids = Arrays.stream(idsAsString.split(","))
                .map(Integer::parseInt)
                .toList();

        List<CartEntity> cartEntities = cartRepository.filter(userId);

        Set<OrderProductEntity> productsInOrder = new HashSet<>();

        double amount = 0.0;

        for (CartEntity cartEntity : cartEntities) {
            ProductEntity productInCart = cartEntity.getProduct();

            if (ids.contains(productInCart.id)) {
                amount += cartEntity.getProduct().price * cartEntity.getQuantity();

                OrderProductEntity productInOrder = new OrderProductEntity();
                productInOrder.setProductId(productInCart.id);
                productInOrder.setName(productInCart.name);
                productInOrder.setPrice(roundPrice(productInCart.price));
                productInOrder.setImage(productInCart.image);
                productInOrder.setQuantity(cartEntity.getQuantity());

                productsInOrder.add(productInOrder);
            }
        }

        if (productsInOrder.isEmpty()) throw new IllegalArgumentException("Product id invalid!");

        List<OrderProductEntity> orderProductSaved = orderProductRepository.saveAll(productsInOrder);
        cartRepository.deleteCartByUserIdAndProductIds(userId, ids);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setProducts(new HashSet<>(orderProductSaved));
        orderEntity.setUserId(userId);
        orderEntity.setStatus("CREATED");
        orderEntity.setAmount(roundPrice(amount));
        orderEntity.setCreatedAt(applicationUtils.dateIsoFormat(new Date()));

        return orderRepository.save(orderEntity);
    }

    public OrderEntity executeOrder(Integer orderId, String password) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        boolean isPasswordValid = bCryptPasswordEncoder.matches(password, userDetails.getPassword());

        if (!isPasswordValid) throw new IllegalArgumentException("Password invalid!");

        OrderEntity order = orderRepository.findById(orderId).orElseThrow();

        if (order.getResult() == null) {
            InvoiceRequest invoiceRequest = new InvoiceRequest();
            invoiceRequest.setExternalId("order-" + orderId);
            invoiceRequest.setDescription("Invoice " + orderId);
            invoiceRequest.setAmount(roundPriceInteger(order.getAmount()));
            invoiceRequest.setCustomer(new InvoiceRequest.Customer(
                    userDetails.getName(), userDetails.getUsername()
            ));

            List<InvoiceRequest.Item> items = order.getProducts().stream().map(this::mapFromOrderProductEntity)
                    .toList();

            invoiceRequest.setItems(items);

            InvoiceResponse invoiceResponse = invoiceService.createInvoice(invoiceRequest);

            String expiredDate = invoiceResponse.getExpiryDate();
            String invoiceUrl = invoiceResponse.getInvoiceUrl();

            OrderResultEntity orderResult = new OrderResultEntity();

            orderResult.setPaymentUrl(invoiceUrl);
            orderResult.setExpiredAt(expiredDate);

            OrderResultEntity orderResultSaved = orderResultRepository.save(orderResult);

            order.setResult(orderResultSaved);
            order.setUpdatedAt(applicationUtils.dateIsoFormat(new Date()));
            order.setStatus("UNPAID");
            return orderRepository.save(order);
        } else {
            return order;
        }
    }

    public OrderEntity handleWebhook(InvoiceWebhookRequest webhookRequest, String callbackToken) {
        if (!callbackToken.trim().equals(xenditWebhookKey.trim())) {
            throw new IllegalArgumentException("Token illegal!");
        }

        String orderId = webhookRequest.getExternalId().split("-")[1];
        OrderEntity order = orderRepository.findById(Integer.parseInt(orderId)).orElseThrow();

        order.setStatus(webhookRequest.getStatus());
        order.setUpdatedAt(webhookRequest.getPaidAt());

        return orderRepository.save(order);
    }

    private double roundPrice(double price) {
        BigDecimal bigDecimal = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    private int roundPriceInteger(double price) {
        BigDecimal bigDecimal = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.intValue();
    }

    private InvoiceRequest.Item mapFromOrderProductEntity(OrderProductEntity product) {
        InvoiceRequest.Item item = new InvoiceRequest.Item();
        item.setName(product.getName());
        item.setPrice(roundPriceInteger(product.getPrice()));
        item.setQuantity(product.getQuantity());
        return item;
    }
}

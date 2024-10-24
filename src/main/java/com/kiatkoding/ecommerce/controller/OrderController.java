package com.kiatkoding.ecommerce.controller;

import com.kiatkoding.ecommerce.model.entity.OrderEntity;
import com.kiatkoding.ecommerce.model.request.OrderExecuteRequest;
import com.kiatkoding.ecommerce.model.response.BaseResponse;
import com.kiatkoding.ecommerce.model.response.PagingInfo;
import com.kiatkoding.ecommerce.model.template.InvoiceWebhookRequest;
import com.kiatkoding.ecommerce.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public BaseResponse getOrders(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        PagingInfo<OrderEntity> orderEntityPagingInfo = orderService.getOrders(page, size);
        return new BaseResponse(true, "Success", orderEntityPagingInfo);
    }

    @PostMapping
    public BaseResponse createOrder(
            @RequestParam String ids
    ) {
        OrderEntity orderEntity = orderService.createOrder(ids.trim());
        return new BaseResponse(true, "Success", orderEntity);
    }

    @PostMapping("/execute")
    public BaseResponse executeOrder(
            @RequestBody OrderExecuteRequest request
    ) {
        OrderEntity orderEntity = orderService.executeOrder(request.getOrderId(), request.getPassword());
        return new BaseResponse(true, "Success", orderEntity);
    }

    @PostMapping("/webhook")
    public BaseResponse webhook(
            @RequestHeader(name = "x-callback-token") String callbackToken,
            @RequestBody InvoiceWebhookRequest webhookRequest
    ) {
        OrderEntity orderEntity = orderService.handleWebhook(webhookRequest, callbackToken);
        return new BaseResponse(true, "Success", orderEntity);
    }
}

package com.kiatkoding.ecommerce.controller;

import com.kiatkoding.ecommerce.model.entity.CartEntity;
import com.kiatkoding.ecommerce.model.request.CartRequest;
import com.kiatkoding.ecommerce.model.response.BaseResponse;
import com.kiatkoding.ecommerce.model.dto.CartDTO;
import com.kiatkoding.ecommerce.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public BaseResponse getCart() {
        CartDTO carts = cartService.getCarts();
        return new BaseResponse(true, "Success", carts);
    }

    @PostMapping
    public BaseResponse insertCart(
            @RequestBody CartRequest cartRequest
    ) {
        Boolean result = cartService.insertCart(cartRequest.productId, cartRequest.quantity);
        return new BaseResponse(true, "Success", result);
    }

    @DeleteMapping
    public BaseResponse deleteCart(
            @RequestBody CartRequest cartRequest
    ) {
        Boolean result = cartService.deleteCart(cartRequest.productId, cartRequest.quantity);
        return new BaseResponse(true, "Success", result);
    }
}

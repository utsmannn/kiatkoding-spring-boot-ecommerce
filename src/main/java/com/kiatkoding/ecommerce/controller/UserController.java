package com.kiatkoding.ecommerce.controller;

import com.kiatkoding.ecommerce.model.dto.UserDTO;
import com.kiatkoding.ecommerce.model.entity.UserEntity;
import com.kiatkoding.ecommerce.model.request.RegisterRequest;
import com.kiatkoding.ecommerce.model.response.BaseResponse;
import com.kiatkoding.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public BaseResponse getUser() {
        UserDTO userDTO = userService.userEntity();
        return new BaseResponse(true, "Success", userDTO);
    }
}

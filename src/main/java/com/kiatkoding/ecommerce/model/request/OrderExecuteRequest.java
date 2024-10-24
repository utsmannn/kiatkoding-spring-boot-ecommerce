package com.kiatkoding.ecommerce.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderExecuteRequest {

    @NotBlank(message = "order_id is required!")
    private Integer orderId;


    @NotBlank(message = "password is required!")
    private String password;
}

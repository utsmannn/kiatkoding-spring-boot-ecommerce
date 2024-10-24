package com.kiatkoding.ecommerce.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CartRequest {

    @NotBlank(message = "ProductId must be provided")
    public Integer productId;

    @NotBlank(message = "Quantity must be provided")
    public Integer quantity;
}

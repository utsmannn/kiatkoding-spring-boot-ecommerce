package com.kiatkoding.ecommerce.model.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProductCartDTO {
    private Integer id;
    private String name;
    private String image;
    private Double price;
    private Integer quantity;
}

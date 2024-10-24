package com.kiatkoding.ecommerce.model.dto;

import com.kiatkoding.ecommerce.model.entity.ProductEntity;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class CartDTO {

    private Double amount;

    private List<ProductCartDTO> products;
}

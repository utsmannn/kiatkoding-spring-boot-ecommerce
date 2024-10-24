package com.kiatkoding.ecommerce.model;

import lombok.Getter;

@Getter
public enum ProductSort {
    PRICE_ASCENDING("price_ascending"),
    PRICE_DESCENDING("price_descending");

    private final String value;

    ProductSort(String value) {
        this.value = value;
    }

    public static ProductSort fromValue(String value) {
        for (ProductSort sort : values()) {
            if (sort.value.equalsIgnoreCase(value)) {
                return sort;
            }
        }

        throw new IllegalArgumentException("Invalid product sort: " + value + ", available: 'price_ascending' or 'price_descending'");
    }
}

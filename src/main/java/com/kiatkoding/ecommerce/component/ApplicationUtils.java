package com.kiatkoding.ecommerce.component;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ApplicationUtils {

    public String dateIsoFormat(Date date) {
        return date.toInstant().toString();
    }
}

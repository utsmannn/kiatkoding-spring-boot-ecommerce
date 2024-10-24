package com.kiatkoding.ecommerce.service;

import com.kiatkoding.ecommerce.configuration.ApplicationConfig;
import com.kiatkoding.ecommerce.model.template.InvoiceRequest;
import com.kiatkoding.ecommerce.model.template.InvoiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final ApplicationConfig applicationConfig;

    @Value("${xendit.apikey.secret}")
    private String xenditApiKey;

    InvoiceResponse createInvoice(InvoiceRequest invoiceRequest) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth(xenditApiKey, "");
        HttpEntity<InvoiceRequest> request = new HttpEntity<>(invoiceRequest, httpHeaders);

        ResponseEntity<InvoiceResponse> response = applicationConfig.restTemplate()
                .postForEntity(
                        URI.create("https://api.xendit.co/v2/invoices"),
                        request,
                        InvoiceResponse.class
                );

        return response.getBody();
    }
}

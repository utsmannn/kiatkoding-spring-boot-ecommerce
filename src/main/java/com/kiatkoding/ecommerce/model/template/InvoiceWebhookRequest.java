package com.kiatkoding.ecommerce.model.template;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InvoiceWebhookRequest {
    private String id;
    private String externalId;
    private String userId;
    private boolean isHigh;
    private String paymentMethod;
    private String status;
    private String merchantName;
    private int amount;
    private int paidAmount;
    private String bankCode;
    private String paidAt;
    private String payerEmail;
    private String description;
    private int adjustedReceivedAmount;
    private int feesPaidAmount;
    private String updated;
    private String created;
    private String currency;
    private String paymentChannel;
    private String paymentDestination;
}


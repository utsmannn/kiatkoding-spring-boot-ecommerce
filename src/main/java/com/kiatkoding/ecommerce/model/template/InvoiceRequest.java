package com.kiatkoding.ecommerce.model.template;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InvoiceRequest {
    private String externalId = "external_id-1";
    private int amount = 20000;
    private String description = "Invoice Demo #123";
    private long invoiceDuration = 86400;
    private Customer customer = new Customer();
    private CustomerNotificationPreference customerNotificationPreference = new CustomerNotificationPreference();
    private String successRedirectUrl = "https://www.google.com";
    private String failureRedirectUrl = "https://www.google.com";
    private String currency = "IDR";
    private List<Item> items = List.of(new Item());
    private List<Fee> fees = List.of(new Fee());

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Customer {
        private String givenNames = "John";
        private String mobileNumber = "+6287774441111";
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CustomerNotificationPreference {
        private List<String> invoiceCreated = List.of("whatsapp", "email", "viber");
        private List<String> invoiceReminder = List.of("whatsapp", "email", "viber");
        private List<String> invoicePaid = List.of("whatsapp", "email", "viber");
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Item {
        private String name = "Air Conditioner";
        private int quantity = 1;
        private int price = 100000;
        private String url = "https://yourcompany.com/example_item";
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Fee {
        private String type = "ADMIN";
        private int value = 5000;
    }
}

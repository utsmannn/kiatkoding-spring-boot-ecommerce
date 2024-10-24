package com.kiatkoding.ecommerce.model.template;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InvoiceResponse {
    private String id;
    private String externalId;
    private String userId;
    private String status;
    private String merchantName;
    private String merchantProfilePictureUrl;
    private int amount;
    private String description;
    private String expiryDate;
    private String invoiceUrl;
    private List<Bank> availableBanks;
    private List<RetailOutlet> availableRetailOutlets;
    private List<Ewallet> availableEwallets;
    private List<QrCode> availableQrCodes;
    private List<DirectDebit> availableDirectDebits;
    private List<Paylater> availablePaylaters;
    private boolean shouldExcludeCreditCard;
    private boolean shouldSendEmail;
    private String successRedirectUrl;
    private String failureRedirectUrl;
    private String created;
    private String updated;
    private String currency;
    private List<Item> items;
    private List<Fee> fees;
    private Customer customer;
    private CustomerNotificationPreference customerNotificationPreference;
    private Object metadata;

    @Data
    public static class Bank {
        private String bankCode;
        private String collectionType;
        private int transferAmount;
        private String bankBranch;
        private String accountHolderName;
        private int identityAmount;
    }

    @Data
    public static class RetailOutlet {
        private String retailOutletName;
    }

    @Data
    public static class Ewallet {
        private String ewalletType;
    }

    @Data
    public static class QrCode {
        private String qrCodeType;
    }

    @Data
    public static class DirectDebit {
        private String directDebitType;
    }

    @Data
    public static class Paylater {
        private String paylaterType;
    }

    @Data
    public static class Item {
        private String name;
        private int quantity;
        private int price;
        private String category;
        private String url;
    }

    @Data
    public static class Fee {
        private String type;
        private int value;
    }

    @Data
    public static class Customer {
        private String givenNames;
        private String mobileNumber;
    }

    @Data
    public static class CustomerNotificationPreference {
        private List<String> invoiceCreated;
        private List<String> invoiceReminder;
        private List<String> invoicePaid;
    }
}

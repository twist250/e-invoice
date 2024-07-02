package com.qtsoftwareltd.invoicing.dto;

import java.math.BigDecimal;

public interface InvoiceDto {
    Long getId();

    CustomerInfo getCustomer();

    String getDescription();

    BigDecimal getAmount();

    interface CustomerInfo {
        Long getId();

        String getName();

        String getEmail();

        String getPhoneNumber();
    }
}

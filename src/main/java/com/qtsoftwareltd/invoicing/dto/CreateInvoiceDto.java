package com.qtsoftwareltd.invoicing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateInvoiceDto {
    @NotNull
    private Long customerId;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal amount;
}

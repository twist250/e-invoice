package com.qtsoftwareltd.invoicing.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCustomerDto {
    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
}

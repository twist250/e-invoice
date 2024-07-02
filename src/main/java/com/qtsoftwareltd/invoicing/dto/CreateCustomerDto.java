package com.qtsoftwareltd.invoicing.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCustomerDto {
    @NotBlank
    private String name;
    @Email
    @NotNull(message = "Email is required")
    private String email;
    @NotBlank
    private String phoneNumber;
    private String password;
}

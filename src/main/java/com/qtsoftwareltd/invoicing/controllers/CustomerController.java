package com.qtsoftwareltd.invoicing.controllers;

import com.qtsoftwareltd.invoicing.dto.*;
import com.qtsoftwareltd.invoicing.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
@Tag(name = "Customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    @Operation(summary = "Get all customers")
    public ResponseEntity<ApiResponse<Page<CustomerDto>>> getCustomers(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(new ApiResponse<>(customerService.getCustomers(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDto>> getCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(customerService.getCustomer(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerDto>> createCustomer(@RequestBody CreateCustomerDto customerDto) {
        return ResponseEntity.ok(new ApiResponse<>(customerService.createCustomer(customerDto)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDto>> updateCustomer(@PathVariable Long id, @RequestBody UpdateCustomerDto customerDto) {
        return ResponseEntity.ok(new ApiResponse<>(customerService.updateCustomer(id, customerDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @GetMapping("/{id}/invoices")
    public ResponseEntity<ApiResponse<Page<InvoiceDto>>> getCustomerInvoices(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(new ApiResponse<>(customerService.getCustomerInvoices(id, pageable)));
    }
}

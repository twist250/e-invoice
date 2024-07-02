package com.qtsoftwareltd.invoicing.controllers;

import com.qtsoftwareltd.invoicing.dto.ApiResponse;
import com.qtsoftwareltd.invoicing.dto.CreateInvoiceDto;
import com.qtsoftwareltd.invoicing.entities.Invoice;
import com.qtsoftwareltd.invoicing.services.InvoiceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/invoices")
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
@Tag(name = "Invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<ApiResponse<Invoice>> createInvoice(@RequestBody CreateInvoiceDto invoiceDto) {
        return ResponseEntity.ok(new ApiResponse<>(invoiceService.createInvoice(invoiceDto)));
    }
}

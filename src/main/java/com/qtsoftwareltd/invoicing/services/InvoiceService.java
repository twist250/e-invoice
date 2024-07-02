package com.qtsoftwareltd.invoicing.services;

import com.qtsoftwareltd.invoicing.dto.CreateInvoiceDto;
import com.qtsoftwareltd.invoicing.entities.Customer;
import com.qtsoftwareltd.invoicing.entities.Invoice;
import com.qtsoftwareltd.invoicing.exceptions.ResourceNotFoundException;
import com.qtsoftwareltd.invoicing.repositories.CustomerRepository;
import com.qtsoftwareltd.invoicing.repositories.InvoiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final NotificationService notificationService;

    @Transactional
    public Invoice createInvoice(CreateInvoiceDto invoiceDto) {
        Customer customer = customerRepository.findById(invoiceDto.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        ModelMapper modelMapper = new ModelMapper();
        Invoice invoice = modelMapper.map(invoiceDto, Invoice.class);
        invoice.setCustomer(customer);
        invoice.setInvoiceDate(LocalDateTime.now());
        Invoice savedInvoice = invoiceRepository.save(invoice);
        notificationService.send(savedInvoice);
        return savedInvoice;
    }
}

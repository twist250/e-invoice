package com.qtsoftwareltd.invoicing.services;

import com.qtsoftwareltd.invoicing.dto.CreateCustomerDto;
import com.qtsoftwareltd.invoicing.dto.CustomerDto;
import com.qtsoftwareltd.invoicing.dto.InvoiceDto;
import com.qtsoftwareltd.invoicing.dto.UpdateCustomerDto;
import com.qtsoftwareltd.invoicing.entities.Customer;
import com.qtsoftwareltd.invoicing.exceptions.BadRequestException;
import com.qtsoftwareltd.invoicing.repositories.CustomerRepository;
import com.qtsoftwareltd.invoicing.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Page<CustomerDto> getCustomers(Pageable page) {
        return customerRepository.findAllCustomers(page);
    }

    public CustomerDto getCustomer(Long id) {
        return customerRepository.findCustomerById(id);
    }

    @Transactional
    public CustomerDto createCustomer(CreateCustomerDto customerDto) {
        userRepository.findByEmail(customerDto.getEmail()).ifPresent(user -> {
            throw new BadRequestException("Email already exists");
        });

        ModelMapper modelMapper = new ModelMapper();
        Customer customer = modelMapper.map(customerDto, Customer.class);
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDto.class);
    }

    @Transactional
    public CustomerDto updateCustomer(Long id, UpdateCustomerDto customerDto) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new BadRequestException("Customer not found"));

        ModelMapper modelMapper = new ModelMapper();
        customer.setName(customerDto.getName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDto.class);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Page<InvoiceDto> getCustomerInvoices(Long id, Pageable pageable) {
        return customerRepository.findCustomerInvoices(id, pageable);
    }
}

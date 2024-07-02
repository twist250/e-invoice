package com.qtsoftwareltd.invoicing.repositories;

import com.qtsoftwareltd.invoicing.dto.CustomerDto;
import com.qtsoftwareltd.invoicing.dto.InvoiceDto;
import com.qtsoftwareltd.invoicing.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT new com.qtsoftwareltd.invoicing.dto.CustomerDto(c.id, c.name, c.email, c.phoneNumber, c.createdAt) FROM Customer c")
    Page<CustomerDto> findAllCustomers(Pageable page);

    @Query("SELECT new com.qtsoftwareltd.invoicing.dto.CustomerDto(c.id, c.name, c.email, c.phoneNumber, c.createdAt) FROM Customer c WHERE c.id = :id")
    CustomerDto findCustomerById(Long id);

    @Query("SELECT i FROM Invoice i WHERE i.customer.id = :id")
    Page<InvoiceDto> findCustomerInvoices(Long id, Pageable pageable);
}
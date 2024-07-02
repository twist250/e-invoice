package com.qtsoftwareltd.invoicing.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.qtsoftwareltd.invoicing.enums.EUserRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("CUSTOMER")
@Getter
@Setter
@ToString
public class Customer extends User {

    public Customer() {
        setRole(EUserRole.CUSTOMER);
    }

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private Set<Invoice> invoices = new HashSet<>();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Customer customer) {
            return getId().equals(customer.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

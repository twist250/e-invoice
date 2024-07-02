package com.qtsoftwareltd.invoicing.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.qtsoftwareltd.invoicing.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@SQLDelete(sql = "UPDATE invoices SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@ToString
public class Invoice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime invoiceDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status = InvoiceStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Invoice invoice) {
            return getId().equals(invoice.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

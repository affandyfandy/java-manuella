package com.fpt.midterm_g1.data.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fpt.midterm_g1.dto.InvoiceDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Invoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "invoice_amount", nullable = false)
    private BigDecimal invoiceAmount;

    @Column(name = "invoice_date", nullable = false)
    private Timestamp invoiceDate;

    @Column(name = "created_time", nullable = false, updatable = false)
    private Timestamp createdTime;

    @Column(name = "updated_time", nullable = false)
    private Timestamp updatedTime;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
    private Set<InvoiceProduct> invoiceProducts;

    @PrePersist
    protected void onCreate() {
        this.createdTime = new Timestamp(System.currentTimeMillis());
        this.updatedTime = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedTime = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", invoiceAmount=" + invoiceAmount +
                ", invoiceDate=" + invoiceDate +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }

    public InvoiceDTO toDTO() {
        return InvoiceDTO.builder()
                .id(this.getId())
                .customer(this.getCustomer() != null ? this.getCustomer().toDTO() : null)
                .invoiceAmount(this.getInvoiceAmount())
                .invoiceDate(this.getInvoiceDate())
                .createdTime(this.getCreatedTime())
                .updatedTime(this.getUpdatedTime())
                .invoiceProducts(Optional.ofNullable(this.getInvoiceProducts())
                        .map(invoiceProduct -> invoiceProduct.stream()
                                .map(InvoiceProduct::toDTO)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .build();
    }
}
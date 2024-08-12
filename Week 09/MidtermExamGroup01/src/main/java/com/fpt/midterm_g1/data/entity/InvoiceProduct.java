package com.fpt.midterm_g1.data.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import com.fpt.midterm_g1.dto.InvoiceProductDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "InvoiceProduct")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(InvoiceProductId.class)
public class InvoiceProduct {
    @Id
    @Column(name = "invoice_id")
    private String invoice_id;

    @Id
    @Column(name = "product_id")
    private int product_id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", insertable = false, updatable = false)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "created_time", nullable = false, updatable = false)
    private Timestamp createdTime;

    @Column(name = "updated_time", nullable = false)
    private Timestamp updatedTime;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceProduct that = (InvoiceProduct) o;
        return Objects.equals(invoice_id, that.invoice_id) && Objects.equals(product_id, that.product_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoice_id, product_id);
    }

    @Override
    public String toString() {
        return "InvoiceProduct{" +
                "invoice_id=" + invoice_id +
                "product_id=" + product_id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }

    public InvoiceProductDTO toDTO() {
        return InvoiceProductDTO.builder()
                .invoice(this.getInvoice() != null ? this.getInvoice().toDTO() : null)
                .product(this.getProduct() != null ? this.getProduct().toDTO() : null)
                .quantity(this.getQuantity())
                .price(this.getPrice())
                .amount(this.getAmount())
                .createdTime(this.getCreatedTime())
                .updatedTime(this.getUpdatedTime())
                .build();
    }
}

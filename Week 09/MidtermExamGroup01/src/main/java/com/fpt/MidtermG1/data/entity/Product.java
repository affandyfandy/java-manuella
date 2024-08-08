package com.fpt.MidtermG1.data.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.common.StatusConverter;
import com.fpt.MidtermG1.dto.ProductDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status;

    @Column(name = "created_time", nullable = false, updatable = false)
    private Timestamp createdTime;

    @Column(name = "updated_time", nullable = false)
    private Timestamp updatedTime;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
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

    public ProductDTO toDTO() {
        return ProductDTO.builder()
                .id(this.getId())
                .name(this.getName())
                .price(this.getPrice())
                .status(this.getStatus())
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

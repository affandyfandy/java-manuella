package com.fpt.midterm_g1.data.entity;

import com.fpt.midterm_g1.common.Status;
import com.fpt.midterm_g1.dto.InvoiceProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.sql.Timestamp;

class InvoiceProductTest {
    private InvoiceProduct invoiceProduct;
    private Invoice invoice;
    private Product product;

    @BeforeEach
    public void setUp() {
        product = Product.builder()
                .id(1)
                .name("Sample Product")
                .price(new BigDecimal("50.00"))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        invoice = Invoice.builder()
                .id("123e4567-e89b-12d3-a456-426614174001")
                .invoiceAmount(new BigDecimal("150.00"))
                .invoiceDate(new Timestamp(System.currentTimeMillis()))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        invoiceProduct = InvoiceProduct.builder()
                .invoice_id(invoice.getId())
                .product_id(product.getId())
                .quantity(3)
                .price(new BigDecimal("50.00"))
                .amount(new BigDecimal("150.00"))
                .invoice(invoice)
                .product(product)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    @Test
    void testInvoiceProductEntity() {
        Assertions.assertThat(invoiceProduct).isNotNull();
        Assertions.assertThat(invoiceProduct.getInvoice_id()).isEqualTo(invoice.getId());
        Assertions.assertThat(invoiceProduct.getProduct_id()).isEqualTo(product.getId());
        Assertions.assertThat(invoiceProduct.getQuantity()).isEqualTo(3);
        Assertions.assertThat(invoiceProduct.getPrice()).isEqualTo(new BigDecimal("50.00"));
        Assertions.assertThat(invoiceProduct.getAmount()).isEqualTo(new BigDecimal("150.00"));
        Assertions.assertThat(invoiceProduct.getInvoice()).isEqualTo(invoice);
        Assertions.assertThat(invoiceProduct.getProduct()).isEqualTo(product);
        Assertions.assertThat(invoiceProduct.getCreatedTime()).isNotNull();
        Assertions.assertThat(invoiceProduct.getUpdatedTime()).isNotNull();
    }

    @Test
    void testInvoiceProductDTOConversion() {
        InvoiceProductDTO dto = invoiceProduct.toDTO();
        Assertions.assertThat(dto).isNotNull();
        Assertions.assertThat(dto.getInvoice()).isEqualTo(invoice.toDTO());
        Assertions.assertThat(dto.getProduct()).isEqualTo(product.toDTO());
        Assertions.assertThat(dto.getQuantity()).isEqualTo(invoiceProduct.getQuantity());
        Assertions.assertThat(dto.getPrice()).isEqualTo(invoiceProduct.getPrice());
        Assertions.assertThat(dto.getAmount()).isEqualTo(invoiceProduct.getAmount());
        Assertions.assertThat(dto.getCreatedTime()).isEqualTo(invoiceProduct.getCreatedTime());
        Assertions.assertThat(dto.getUpdatedTime()).isEqualTo(invoiceProduct.getUpdatedTime());
    }

    @Test
    void testInvoiceProductPrePersist() {
        InvoiceProduct newInvoiceProduct = new InvoiceProduct();
        newInvoiceProduct.onCreate();
        Assertions.assertThat(newInvoiceProduct.getCreatedTime()).isNotNull();
        Assertions.assertThat(newInvoiceProduct.getUpdatedTime()).isNotNull();
    }

    @Test
    void testInvoiceProductPreUpdate() {
        Timestamp initialUpdatedTime = invoiceProduct.getUpdatedTime();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        invoiceProduct.preUpdate();
        Timestamp updatedTimeAfterUpdate = invoiceProduct.getUpdatedTime();
        Assertions.assertThat(updatedTimeAfterUpdate)
                .isAfter(initialUpdatedTime);
    }
}
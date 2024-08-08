package com.fpt.MidtermG1.data.entity;

import com.fpt.MidtermG1.common.Status;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvoiceInvoiceProductRelationTest {
    @Test
    public void testInvoiceInvoiceProductRelation() {
        Invoice invoice = Invoice.builder()
                .id("invoice123")
                .invoiceAmount(new BigDecimal("200.00"))
                .invoiceDate(new Timestamp(System.currentTimeMillis()))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        Product product = Product.builder()
                .id(1)
                .name("Test Product")
                .price(new BigDecimal("100.00"))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        InvoiceProduct invoiceProduct = InvoiceProduct.builder()
                .invoice(invoice)
                .product(product)
                .quantity(1)
                .price(new BigDecimal("100.00"))
                .amount(new BigDecimal("100.00"))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        assertEquals(invoice.getId(), invoiceProduct.getInvoice().getId());
        assertEquals(product.getId(), invoiceProduct.getProduct().getId());
    }
}
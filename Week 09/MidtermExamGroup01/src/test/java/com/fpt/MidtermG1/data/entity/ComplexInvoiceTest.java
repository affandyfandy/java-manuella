package com.fpt.MidtermG1.data.entity;

import com.fpt.MidtermG1.common.Status;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComplexInvoiceTest {
    @Test
    public void testComplexInvoiceAndProducts() {
        Product product1 = Product.builder()
                .id(1)
                .name("Product 1")
                .price(new BigDecimal("100.00"))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        Product product2 = Product.builder()
                .id(2)
                .name("Product 2")
                .price(new BigDecimal("200.00"))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        Invoice invoice1 = Invoice.builder()
                .id("invoice123")
                .invoiceAmount(new BigDecimal("300.00"))
                .invoiceDate(new Timestamp(System.currentTimeMillis()))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        Invoice invoice2 = Invoice.builder()
                .id("invoice124")
                .invoiceAmount(new BigDecimal("200.00"))
                .invoiceDate(new Timestamp(System.currentTimeMillis()))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        InvoiceProduct invoiceProduct1 = InvoiceProduct.builder()
                .invoice(invoice1)
                .product(product1)
                .quantity(2)
                .price(new BigDecimal("100.00"))
                .amount(new BigDecimal("200.00"))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        InvoiceProduct invoiceProduct2 = InvoiceProduct.builder()
                .invoice(invoice1)
                .product(product2)
                .quantity(1)
                .price(new BigDecimal("200.00"))
                .amount(new BigDecimal("200.00"))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        InvoiceProduct invoiceProduct3 = InvoiceProduct.builder()
                .invoice(invoice2)
                .product(product1)
                .quantity(1)
                .price(new BigDecimal("100.00"))
                .amount(new BigDecimal("100.00"))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        Set<InvoiceProduct> invoiceProductsForInvoice1 = new HashSet<>();
        invoiceProductsForInvoice1.add(invoiceProduct1);
        invoiceProductsForInvoice1.add(invoiceProduct2);

        Set<InvoiceProduct> invoiceProductsForInvoice2 = new HashSet<>();
        invoiceProductsForInvoice2.add(invoiceProduct3);

        invoice1.setInvoiceProducts(invoiceProductsForInvoice1);
        invoice2.setInvoiceProducts(invoiceProductsForInvoice2);

        assertEquals(2, invoice1.getInvoiceProducts().size());
        assertEquals(1, invoice2.getInvoiceProducts().size());

        assertEquals(invoice1.getId(), invoiceProduct1.getInvoice().getId());
        assertEquals(invoice1.getId(), invoiceProduct2.getInvoice().getId());
        assertEquals(invoice2.getId(), invoiceProduct3.getInvoice().getId());

        assertEquals(product1.getId(), invoiceProduct1.getProduct().getId());
        assertEquals(product2.getId(), invoiceProduct2.getProduct().getId());
        assertEquals(product1.getId(), invoiceProduct3.getProduct().getId());
    }
}
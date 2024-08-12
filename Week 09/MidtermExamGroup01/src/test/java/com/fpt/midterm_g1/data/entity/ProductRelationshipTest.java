package com.fpt.midterm_g1.data.entity;

import com.fpt.midterm_g1.common.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

class ProductRelationshipTest {
    private Product product;
    private InvoiceProduct invoiceProduct1;
    private InvoiceProduct invoiceProduct2;

    @BeforeEach
    public void setUp() {
        product = Product.builder()
                .id(1)
                .name("Product A")
                .price(new BigDecimal("99.99"))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        invoiceProduct1 = InvoiceProduct.builder()
                .invoice_id("invoice-001")
                .product_id(1)
                .quantity(5)
                .price(new BigDecimal("99.99"))
                .amount(new BigDecimal("499.95"))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .product(product)
                .build();

        invoiceProduct2 = InvoiceProduct.builder()
                .invoice_id("invoice-002")
                .product_id(1)
                .quantity(3)
                .price(new BigDecimal("99.99"))
                .amount(new BigDecimal("299.97"))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .product(product)
                .build();
    }

    @Test
    void testProductInvoiceProductRelationship() {
        Set<InvoiceProduct> invoiceProducts = new HashSet<>();
        invoiceProducts.add(invoiceProduct1);
        invoiceProducts.add(invoiceProduct2);
        product.setInvoiceProducts(invoiceProducts);

        Assertions.assertThat(product.getInvoiceProducts()).containsExactlyInAnyOrder(invoiceProduct1, invoiceProduct2);
        Assertions.assertThat(invoiceProduct1.getProduct()).isEqualTo(product);
        Assertions.assertThat(invoiceProduct2.getProduct()).isEqualTo(product);
    }
}

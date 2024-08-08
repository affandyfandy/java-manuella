package com.fpt.MidtermG1.dto;

import com.fpt.MidtermG1.data.entity.Invoice;
import com.fpt.MidtermG1.data.entity.InvoiceProduct;
import com.fpt.MidtermG1.data.entity.Product;
import com.fpt.MidtermG1.common.Status;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComplexDTOTest {
    @Test
    public void testComplexDtoConversion() {
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

        InvoiceDTO invoiceDTO1 = invoice1.toDTO();
        InvoiceDTO invoiceDTO2 = invoice2.toDTO();

        assertEquals(invoice1.getId(), invoiceDTO1.getId());
        assertEquals(invoice1.getInvoiceAmount(), invoiceDTO1.getInvoiceAmount());
        assertEquals(invoice1.getInvoiceProducts().size(), invoiceDTO1.getInvoiceProducts().size());

        Set<Integer> productIdsFromDTO1 = invoiceDTO1.getInvoiceProducts().stream()
                .map(dto -> dto.getProduct().getId())
                .collect(Collectors.toSet());

        Set<Integer> productIdsFromInvoiceProduct1 = invoice1.getInvoiceProducts().stream()
                .map(ip -> ip.getProduct().getId())
                .collect(Collectors.toSet());

        assertEquals(productIdsFromInvoiceProduct1, productIdsFromDTO1);
    }
}
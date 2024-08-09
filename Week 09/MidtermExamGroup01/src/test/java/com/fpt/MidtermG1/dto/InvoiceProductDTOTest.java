package com.fpt.MidtermG1.dto;

import com.fpt.MidtermG1.data.entity.InvoiceProduct;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvoiceProductDTOTest {
    @Test
    public void testToEntity() {
        InvoiceProductDTO dto = InvoiceProductDTO.builder()
                .invoice(InvoiceDTO.builder().id("invoice123").build())
                .product(ProductDTO.builder().id(1).build())
                .quantity(1)
                .price(new BigDecimal("150.00"))
                .amount(new BigDecimal("150.00"))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        InvoiceProduct invoiceProduct = dto.toEntity();

        assertEquals(dto.getInvoice().getId(), invoiceProduct.getInvoice().getId());
        assertEquals(dto.getProduct().getId(), invoiceProduct.getProduct().getId());
        assertEquals(dto.getQuantity(), invoiceProduct.getQuantity());
        assertEquals(dto.getPrice(), invoiceProduct.getPrice());
        assertEquals(dto.getAmount(), invoiceProduct.getAmount());
        assertEquals(dto.getCreatedTime(), invoiceProduct.getCreatedTime());
        assertEquals(dto.getUpdatedTime(), invoiceProduct.getUpdatedTime());
    }
}
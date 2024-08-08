package com.fpt.MidtermG1.dto;

import com.fpt.MidtermG1.data.entity.Invoice;
import com.fpt.MidtermG1.common.Status;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvoiceDTOTest {

    @Test
    public void testToEntity() {
        InvoiceDTO dto = InvoiceDTO.builder()
                .id("invoice123")
                .customer(CustomerDTO.builder()
                        .id("customer123")
                        .name("Jane Doe")
                        .phoneNumber("+0987654321")
                        .status(Status.ACTIVE)
                        .build())
                .invoiceAmount(new BigDecimal("200.00"))
                .invoiceDate(new Timestamp(System.currentTimeMillis()))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .invoiceProducts(Collections.emptyList())
                .build();

        Invoice invoice = dto.toEntity();

        assertEquals(dto.getId(), invoice.getId());
        assertEquals(dto.getInvoiceAmount(), invoice.getInvoiceAmount());
        assertEquals(dto.getInvoiceDate(), invoice.getInvoiceDate());
        assertEquals(dto.getCreatedTime(), invoice.getCreatedTime());
        assertEquals(dto.getUpdatedTime(), invoice.getUpdatedTime());
    }
}

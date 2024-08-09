package com.fpt.MidtermG1.data.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InvoiceTest {
    @Test
    void testInvoiceCreation() {
        Customer customer = Customer.builder()
                .id("customerId")
                .build();

        Invoice invoice = Invoice.builder()
                .id("invoiceId")
                .customer(customer)
                .invoiceAmount(BigDecimal.valueOf(100))
                .invoiceDate(Timestamp.from(Instant.now()))
                .createdTime(Timestamp.from(Instant.now()))
                .updatedTime(Timestamp.from(Instant.now()))
                .build();

        assertEquals("invoiceId", invoice.getId());
        assertEquals(customer, invoice.getCustomer());
        assertEquals(BigDecimal.valueOf(100), invoice.getInvoiceAmount());
        assertNotNull(invoice.getInvoiceDate());
        assertNotNull(invoice.getCreatedTime());
        assertNotNull(invoice.getUpdatedTime());
    }
}
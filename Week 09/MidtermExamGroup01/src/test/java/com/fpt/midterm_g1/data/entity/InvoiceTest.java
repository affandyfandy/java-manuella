package com.fpt.midterm_g1.data.entity;

import com.fpt.midterm_g1.common.Status;
import com.fpt.midterm_g1.dto.InvoiceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;

class InvoiceTest {
    private Invoice invoice;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .name("John Doe")
                .phoneNumber("1234567890")
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        invoice = Invoice.builder()
                .id("123e4567-e89b-12d3-a456-426614174001")
                .customer(customer)
                .invoiceAmount(new BigDecimal("150.00"))
                .invoiceDate(new Timestamp(System.currentTimeMillis()))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .invoiceProducts(Collections.emptySet())
                .build();
    }

    @Test
    void testInvoiceEntity() {
        Assertions.assertThat(invoice).isNotNull();
        Assertions.assertThat(invoice.getId()).isEqualTo("123e4567-e89b-12d3-a456-426614174001");
        Assertions.assertThat(invoice.getCustomer()).isEqualTo(customer);
        Assertions.assertThat(invoice.getInvoiceAmount()).isEqualTo(new BigDecimal("150.00"));
        Assertions.assertThat(invoice.getInvoiceDate()).isNotNull();
        Assertions.assertThat(invoice.getCreatedTime()).isNotNull();
        Assertions.assertThat(invoice.getUpdatedTime()).isNotNull();
        Assertions.assertThat(invoice.getInvoiceProducts()).isEmpty();
    }

    @Test
    void testInvoiceDTOConversion() {
        InvoiceDTO dto = invoice.toDTO();
        Assertions.assertThat(dto).isNotNull();
        Assertions.assertThat(dto.getId()).isEqualTo(invoice.getId());
        Assertions.assertThat(dto.getCustomer()).isEqualTo(customer.toDTO());
        Assertions.assertThat(dto.getInvoiceAmount()).isEqualTo(invoice.getInvoiceAmount());
        Assertions.assertThat(dto.getInvoiceDate()).isEqualTo(invoice.getInvoiceDate());
        Assertions.assertThat(dto.getCreatedTime()).isEqualTo(invoice.getCreatedTime());
        Assertions.assertThat(dto.getUpdatedTime()).isEqualTo(invoice.getUpdatedTime());
        Assertions.assertThat(dto.getInvoiceProducts()).isEmpty();
    }

    @Test
    void testInvoicePrePersist() {
        Invoice newInvoice = new Invoice();
        newInvoice.onCreate();
        Assertions.assertThat(newInvoice.getCreatedTime()).isNotNull();
        Assertions.assertThat(newInvoice.getUpdatedTime()).isNotNull();
    }

    @Test
    void testInvoicePreUpdate() {
        Timestamp initialUpdatedTime = invoice.getUpdatedTime();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        invoice.preUpdate();
        Timestamp updatedTimeAfterUpdate = invoice.getUpdatedTime();
        Assertions.assertThat(updatedTimeAfterUpdate)
                .isAfter(initialUpdatedTime);
    }
}

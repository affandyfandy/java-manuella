package com.fpt.MidtermG1.data.entity;

import com.fpt.MidtermG1.common.Status;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerInvoiceRelationTest {
    @Test
    public void testCustomerInvoiceRelation() {
        Customer customer = Customer.builder()
                .id("customer123")
                .name("John Doe")
                .phoneNumber("+1234567890")
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        Invoice invoice = Invoice.builder()
                .id("invoice123")
                .customer(customer)
                .invoiceAmount(new BigDecimal("200.00"))
                .invoiceDate(new Timestamp(System.currentTimeMillis()))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        assertEquals(customer.getId(), invoice.getCustomer().getId());
        assertEquals(customer.getName(), invoice.getCustomer().getName());
        assertEquals(customer.getPhoneNumber(), invoice.getCustomer().getPhoneNumber());
    }
}

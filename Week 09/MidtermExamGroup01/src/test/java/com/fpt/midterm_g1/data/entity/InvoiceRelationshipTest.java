package com.fpt.midterm_g1.data.entity;

import com.fpt.midterm_g1.common.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

class InvoiceRelationshipTest {
    private Set<Customer> customers;
    private Set<Invoice> invoices;

    @BeforeEach
    public void setUp() {
        customers = new HashSet<>();
        invoices = new HashSet<>();

        Customer customer1 = Customer.builder()
                .id("customer-001")
                .name("Customer A")
                .phoneNumber("123456789")
                .status(Status.ACTIVE)
                .createdTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .updatedTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .build();

        Customer customer2 = Customer.builder()
                .id("customer-002")
                .name("Customer B")
                .phoneNumber("987654321")
                .status(Status.ACTIVE)
                .createdTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .updatedTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .build();

        customers.add(customer1);
        customers.add(customer2);

        Invoice invoice1 = Invoice.builder()
                .id("invoice-001")
                .customer(customer1)
                .invoiceAmount(new BigDecimal("499.95"))
                .invoiceDate(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .createdTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .updatedTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .build();

        Invoice invoice2 = Invoice.builder()
                .id("invoice-002")
                .customer(customer1)
                .invoiceAmount(new BigDecimal("299.50"))
                .invoiceDate(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .createdTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .updatedTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .build();

        Invoice invoice3 = Invoice.builder()
                .id("invoice-003")
                .customer(customer2)
                .invoiceAmount(new BigDecimal("199.95"))
                .invoiceDate(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .createdTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .updatedTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .build();

        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
    }

    @Test
    void testCustomerInvoiceRelationships() {
        for (Customer customer : customers) {
            Set<Invoice> customerInvoices = new HashSet<>();
            for (Invoice invoice : invoices) {
                if (invoice.getCustomer().equals(customer)) {
                    customerInvoices.add(invoice);
                }
            }

            // Assertions for each customer
            Assertions.assertThat(customerInvoices.size()).isGreaterThan(0);
            for (Invoice invoice : customerInvoices) {
                Assertions.assertThat(invoice.getCustomer()).isEqualTo(customer);
            }
        }

        // Validate invoices and their customers
        for (Invoice invoice : invoices) {
            Assertions.assertThat(invoice.getCustomer()).isNotNull();
            Assertions.assertThat(customers).contains(invoice.getCustomer());
            Assertions.assertThat(invoice.getInvoiceAmount()).isGreaterThan(BigDecimal.ZERO);
        }
    }
}
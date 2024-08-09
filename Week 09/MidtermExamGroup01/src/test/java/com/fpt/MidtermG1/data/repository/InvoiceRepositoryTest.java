package com.fpt.MidtermG1.data.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import com.fpt.MidtermG1.data.entity.Customer;
import com.fpt.MidtermG1.data.entity.Invoice;

@DataJpaTest
class InvoiceRepositoryTest {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder()
                .id("customerId")
                .build();

        customerRepository.save(customer);

        Invoice invoice1 = Invoice.builder()
                .id("invoiceId1")
                .customer(customer)
                .invoiceAmount(BigDecimal.valueOf(100))
                .invoiceDate(Timestamp.from(Instant.now()))
                .createdTime(Timestamp.from(Instant.now()))
                .updatedTime(Timestamp.from(Instant.now()))
                .build();

        Invoice invoice2 = Invoice.builder()
                .id("invoiceId2")
                .customer(customer)
                .invoiceAmount(BigDecimal.valueOf(200))
                .invoiceDate(Timestamp.from(Instant.now()))
                .createdTime(Timestamp.from(Instant.now()))
                .updatedTime(Timestamp.from(Instant.now()))
                .build();

        invoiceRepository.save(invoice1);
        invoiceRepository.save(invoice2);
    }

//    @Test
//    void testFindByInvoiceDateBetween() {
//        Timestamp startDate = Timestamp.from(Instant.now().minusSeconds(3600));
//        Timestamp endDate = Timestamp.from(Instant.now().plusSeconds(3600));
//
//        List<Invoice> invoices = invoiceRepository.findByInvoiceDateBetween(startDate, endDate);
//
//        assertEquals(2, invoices.size());
//    }
}
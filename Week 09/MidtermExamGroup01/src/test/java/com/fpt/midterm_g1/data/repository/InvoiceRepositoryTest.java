package com.fpt.midterm_g1.data.repository;

import com.fpt.midterm_g1.common.Status;
import com.fpt.midterm_g1.data.entity.Customer;
import com.fpt.midterm_g1.data.entity.Invoice;
import com.fpt.midterm_g1.data.specification.InvoiceSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class InvoiceRepositoryTest {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;
    private Invoice invoice1;
    private Invoice invoice2;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .name("John Doe")
                .phoneNumber("123456789")
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        customerRepository.save(customer);

        invoice1 = Invoice.builder()
                .customer(customer)
                .invoiceAmount(BigDecimal.valueOf(150.00))
                .invoiceDate(new Timestamp(System.currentTimeMillis()))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        invoice2 = Invoice.builder()
                .customer(customer)
                .invoiceAmount(BigDecimal.valueOf(250.00))
                .invoiceDate(new Timestamp(System.currentTimeMillis()))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        invoiceRepository.save(invoice1);
        invoiceRepository.save(invoice2);
    }

    @Test
    void testFindById() {
        Invoice fetchedInvoice = invoiceRepository.findById(invoice1.getId()).orElse(null);
        assertThat(fetchedInvoice).isNotNull();
        assertThat(fetchedInvoice.getId()).isEqualTo(invoice1.getId());
    }

    @Test
    void testFindAll() {
        List<Invoice> invoices = invoiceRepository.findAll();
        assertThat(invoices).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void testAdd() {
        Invoice newInvoice = Invoice.builder()
                .customer(customer)
                .invoiceAmount(BigDecimal.valueOf(300.00))
                .invoiceDate(new Timestamp(System.currentTimeMillis()))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        Invoice savedInvoice = invoiceRepository.save(newInvoice);
        assertThat(savedInvoice).isNotNull();
        assertThat(savedInvoice.getId()).isEqualTo(newInvoice.getId());
    }

    @Test
    void testEdit() {
        invoice1.setInvoiceAmount(BigDecimal.valueOf(200.00));
        invoiceRepository.save(invoice1);

        Invoice updatedInvoice = invoiceRepository.findById(invoice1.getId()).orElse(null);
        assertThat(updatedInvoice).isNotNull();
        assertThat(updatedInvoice.getInvoiceAmount()).isEqualTo(BigDecimal.valueOf(200.00));
    }

    @Test
    void testActivate() {
        invoice1.setInvoiceAmount(BigDecimal.valueOf(180.00));
        invoiceRepository.save(invoice1);

        Invoice updatedInvoice = invoiceRepository.findById(invoice1.getId()).orElse(null);
        assertThat(updatedInvoice).isNotNull();
        assertThat(updatedInvoice.getInvoiceAmount()).isEqualTo(BigDecimal.valueOf(180.00));
    }

    @Test
    void testDeactivate() {
        invoice2.setInvoiceAmount(BigDecimal.valueOf(220.00));
        invoiceRepository.save(invoice2);

        Invoice updatedInvoice = invoiceRepository.findById(invoice2.getId()).orElse(null);
        assertThat(updatedInvoice).isNotNull();
        assertThat(updatedInvoice.getInvoiceAmount()).isEqualTo(BigDecimal.valueOf(220.00));
    }

    @Test
    void testSearchBySpec() {
        Specification<Invoice> spec = Specification.where(InvoiceSpecification.hasCustomerId(customer.getId()))
                .and(InvoiceSpecification.hasInvoiceYear(2024));
        List<Invoice> invoices = invoiceRepository.findAll(spec);
        assertThat(invoices).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void testFindByInvoiceDateBetween() {
        Timestamp startDate = Timestamp.valueOf("2023-01-01 00:00:00");
        Timestamp endDate = Timestamp.valueOf(new Timestamp(System.currentTimeMillis()).toLocalDateTime());

        List<Invoice> invoices = invoiceRepository.findByInvoiceDateBetween(startDate, endDate);

        assertThat(invoices).hasSize(2);
        assertThat(invoices.get(0).getId()).isEqualTo(invoice1.getId());
    }
}

package com.fpt.midterm_g1.service.impl;

import com.fpt.midterm_g1.common.Status;
import com.fpt.midterm_g1.data.entity.*;
import com.fpt.midterm_g1.data.repository.*;
import com.fpt.midterm_g1.dto.*;
import com.fpt.midterm_g1.exception.InactiveCustomerException;
import com.fpt.midterm_g1.exception.InactiveProductException;
import com.fpt.midterm_g1.exception.ResourceNotFoundException;
import com.fpt.midterm_g1.util.PDFUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {InvoiceServiceImpl.class})
class InvoiceServiceImplTest {
    @MockBean
    private InvoiceRepository invoiceRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private InvoiceProductRepository invoiceProductRepository;

    @MockBean
    private PDFUtils pdfUtils;

    @Autowired
    private InvoiceServiceImpl invoiceService;

    private Invoice invoice;
    private Customer customer;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = Customer.builder()
                .id("123e4567-e89b-12d3-a456-426614174001")
                .name("John Doe")
                .phoneNumber("+1234567890")
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        product = Product.builder()
                .id(1)
                .price(BigDecimal.TEN)
                .status(Status.ACTIVE)
                .build();

        invoice = Invoice.builder()
                .id("123e4567-e89b-12d3-a456-426614174001")
                .customer(customer)
                .invoiceAmount(BigDecimal.valueOf(100))
                .invoiceDate(Timestamp.from(Instant.now()))
                .createdTime(Timestamp.from(Instant.now()))
                .updatedTime(Timestamp.from(Instant.now()))
                .invoiceProducts(Collections.emptySet())
                .build();
    }

    @Test
    void addInvoice_ShouldReturnInvoice() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);

        Invoice result = invoiceService.addInvoice(invoice.toDTO()).toEntity();

        assertNotNull(result);
        assertEquals(invoice.getId(), result.getId());
        verify(invoiceRepository).save(any(Invoice.class));
        verify(invoiceProductRepository, times(1)).save(any(InvoiceProduct.class));
    }

    @Test
    void addInvoice_ProductNotFound_ShouldThrowResourceNotFoundException() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> invoiceService.addInvoice(invoice.toDTO()));
    }

    @Test
    void addInvoice_CustomerNotFound_ShouldThrowResourceNotFoundException() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(customerRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> invoiceService.addInvoice(invoice.toDTO()));
    }

    @Test
    void editInvoice_ShouldReturnInvoice() {
        when(invoiceRepository.findById(anyString())).thenReturn(Optional.of(invoice));
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customer));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);

        Invoice result = invoiceService.editInvoice("123e4567-e89b-12d3-a456-426614174001", invoice.toDTO()).toEntity();

        assertNotNull(result);
        assertEquals(invoice.getId(), result.getId());
        verify(invoiceRepository).save(any(Invoice.class));
        verify(invoiceProductRepository, times(1)).save(any(InvoiceProduct.class));
    }

    @Test
    void editInvoice_InvoiceNotFound_ShouldThrowResourceNotFoundException() {
        when(invoiceRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> invoiceService.editInvoice("invoice-id", invoice));
    }

    @Test
    void getInvoiceById_ShouldReturnInvoice() {
        when(invoiceRepository.findById(anyString())).thenReturn(Optional.of(invoice));

        Invoice result = invoiceService.getInvoiceById("123e4567-e89b-12d3-a456-426614174001").toEntity();

        assertNotNull(result);
        assertEquals(invoice.getId(), result.getId());
        verify(invoiceRepository).findById(anyString());
    }

    @Test
    void getInvoiceById_InvoiceNotFound_ShouldThrowResourceNotFoundException() {
        when(invoiceRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> invoiceService.getInvoiceById("invoice-id"));
    }

    @Test
    void getAllInvoices_ShouldReturnListOfInvoices() {
        List<Invoice> invoices = Collections.singletonList(invoice);
        Page<Invoice> invoicePage = new PageImpl<>(invoices);
        when(invoiceRepository.findAll(any(Pageable.class))).thenReturn(invoicePage);

        List<InvoiceDTO> result = invoiceService.getAllInvoices(0, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(invoiceRepository).findAll(any(Pageable.class));
    }

    @Test
    void addInvoiceProduct_ShouldReturnInvoiceProduct() {
        InvoiceProduct invoiceProduct = new InvoiceProduct();
        when(invoiceRepository.findById(anyString())).thenReturn(Optional.of(invoice));
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(invoiceProductRepository.save(any(InvoiceProduct.class))).thenReturn(invoiceProduct);

        InvoiceProduct result = invoiceService.addInvoiceProduct(new InvoiceProduct().toDTO()).toEntity();

        assertNotNull(result);
        verify(invoiceProductRepository).save(any(InvoiceProduct.class));
    }

    @Test
    void addInvoiceProduct_ProductNotFound_ShouldThrowResourceNotFoundException() {
        when(invoiceRepository.findById(anyString())).thenReturn(Optional.of(invoice));
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> invoiceService.addInvoiceProduct(new InvoiceProduct()));
    }

    @Test
    void getInvoicesByCriteria_ShouldReturnListOfInvoices() {
        List<Invoice> invoices = Collections.singletonList(invoice);
        Page<Invoice> invoicePage = new PageImpl<>(invoices);
        when(invoiceRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(invoicePage);

        List<Invoice> result = invoiceService.getInvoicesByCriteria("customer-id", "Customer Name", 2024, 8, ">", BigDecimal.TEN, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(invoiceRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void exportAllInvoicesToPDF_ShouldReturnByteArray() throws IOException {
        when(invoiceProductRepository.findAllWithDetails()).thenReturn(Collections.emptyList());
        when(pdfUtils.generateAllInvoicesPDF(anyList())).thenReturn(new byte[0]);

        byte[] result = invoiceService.exportAllInvoicesToPDF();

        assertNotNull(result);
        verify(pdfUtils).generateAllInvoicesPDF(anyList());
    }

    @Test
    void exportAllInvoicesToPDF_Failure_ShouldThrowResponseStatusException() throws IOException {
        when(invoiceProductRepository.findAllWithDetails()).thenReturn(Collections.emptyList());
        when(pdfUtils.generateAllInvoicesPDF(anyList())).thenThrow(new IOException("PDF generation failed"));

        assertThrows(ResponseStatusException.class, () -> invoiceService.exportAllInvoicesToPDF());
    }

    @Test
    void getRevenueByPeriod_ShouldReturnListOfRevenueReportDTO() {
        List<Invoice> invoices = Collections.singletonList(invoice);
        when(invoiceRepository.findByInvoiceDateBetween(any(Timestamp.class), any(Timestamp.class))).thenReturn(invoices);

        List<RevenueReportDTO> result = invoiceService.getRevenueByPeriod(2024, 8, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(invoiceRepository).findByInvoiceDateBetween(any(Timestamp.class), any(Timestamp.class));
    }
}

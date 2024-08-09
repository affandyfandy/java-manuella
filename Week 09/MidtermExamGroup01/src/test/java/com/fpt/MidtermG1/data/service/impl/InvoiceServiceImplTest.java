//package com.fpt.MidtermG1.data.service.impl;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.util.*;
//
//import com.fpt.MidtermG1.dto.*;
//import com.fpt.MidtermG1.service.impl.InvoiceServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.server.ResponseStatusException;
//
//import com.fpt.MidtermG1.common.Status;
//import com.fpt.MidtermG1.data.entity.*;
//import com.fpt.MidtermG1.data.repository.*;
//import com.fpt.MidtermG1.exception.InactiveCustomerException;
//import com.fpt.MidtermG1.exception.InactiveProductException;
//import com.fpt.MidtermG1.exception.ResourceNotFoundException;
//import com.fpt.MidtermG1.service.InvoiceService;
//import com.fpt.MidtermG1.util.PDFUtils;
//
//class InvoiceServiceImplTest {
//
//    @Mock
//    private InvoiceRepository invoiceRepository;
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private InvoiceProductRepository invoiceProductRepository;
//
//    @Mock
//    private PDFUtils pdfUtils;
//
//    @InjectMocks
//    private InvoiceServiceImpl invoiceService;
//
//    @Test
//    void testAddInvoice_Success() {
//        Customer customer = Customer.builder()
//                .id("customerId")
//                .status(Status.ACTIVE)
//                .build();
//
//        Product product = Product.builder()
//                .id(1)
//                .price(BigDecimal.TEN)
//                .status(Status.ACTIVE)
//                .build();
//
//        InvoiceDTO invoiceDTO = InvoiceDTO.builder()
//                .customer(CustomerDTO.builder().id("customerId").build())
//                .invoiceProducts(Arrays.asList(
//                        InvoiceProductDTO.builder()
//                                .product(ProductDTO.builder().id(1).build())
//                                .quantity(2)
//                                .build()
//                ))
//                .build();
//
//        Invoice invoice = Invoice.builder()
//                .id("invoiceId")
//                .invoiceAmount(BigDecimal.valueOf(20))
//                .invoiceDate(Timestamp.from(Instant.now()))
//                .createdTime(Timestamp.from(Instant.now()))
//                .updatedTime(Timestamp.from(Instant.now()))
//                .customer(customer)
//                .build();
//
//        when(productRepository.findById(1)).thenReturn(Optional.of(product));
//        when(customerRepository.findById("customerId")).thenReturn(Optional.of(customer));
//        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);
//        when(invoiceProductRepository.save(any(InvoiceProduct.class))).thenReturn(new InvoiceProduct());
//
//        InvoiceDTO result = invoiceService.addInvoice(invoiceDTO);
//
//        assertEquals(invoice.getId(), result.getId());
//        verify(invoiceRepository).save(any(Invoice.class));
//    }
//
//    @Test
//    void testAddInvoice_CustomerInactive() {
//        Customer inactiveCustomer = Customer.builder()
//                .id("customerId")
//                .status(Status.INACTIVE)
//                .build();
//
//        InvoiceDTO invoiceDTO = InvoiceDTO.builder()
//                .customer(CustomerDTO.builder().id("customerId").build())
//                .build();
//
//        when(customerRepository.findById("customerId")).thenReturn(Optional.of(inactiveCustomer));
//
//        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
//            invoiceService.addInvoice(invoiceDTO);
//        });
//
//        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
//        assertEquals("Customer is inactive", thrown.getReason());
//    }
//
//    @Test
//    void testEditInvoice_Success() {
//        // Mock data
//        Customer customer = Customer.builder()
//                .id("customerId")
//                .status(Status.ACTIVE)
//                .build();
//
//        Invoice existingInvoice = Invoice.builder()
//                .id("invoiceId")
//                .invoiceDate(Timestamp.from(Instant.now().minusSeconds(60)))
//                .invoiceAmount(BigDecimal.valueOf(10))
//                .customer(customer)
//                .build();
//
//        InvoiceDTO updatedInvoiceDTO = InvoiceDTO.builder()
//                .customer(CustomerDTO.builder().id("customerId").build())
//                .invoiceProducts(Collections.emptyList())
//                .build();
//
//        when(invoiceRepository.findById("invoiceId")).thenReturn(Optional.of(existingInvoice));
//        when(customerRepository.findById("customerId")).thenReturn(Optional.of(customer));
//        when(invoiceRepository.save(any(Invoice.class))).thenReturn(existingInvoice);
//
//        InvoiceDTO result = invoiceService.editInvoice("invoiceId", updatedInvoiceDTO);
//
//        assertEquals(existingInvoice.getId(), result.getId());
//        verify(invoiceRepository).save(any(Invoice.class));
//    }
//
//    @Test
//    void testEditInvoice_InvoiceTooOld() {
//        // Mock data
//        Invoice existingInvoice = Invoice.builder()
//                .id("invoiceId")
//                .invoiceDate(Timestamp.from(Instant.now().minusSeconds(600)))
//                .build();
//
//        InvoiceDTO updatedInvoiceDTO = InvoiceDTO.builder().build();
//
//        when(invoiceRepository.findById("invoiceId")).thenReturn(Optional.of(existingInvoice));
//
//        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
//            invoiceService.editInvoice("invoiceId", updatedInvoiceDTO);
//        });
//
//        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
//        assertEquals("Invoice can only be edited within 10 minutes of creation", thrown.getReason());
//    }
//
//    @Test
//    void testGetInvoiceById_Success() {
//        // Mock data
//        Invoice invoice = Invoice.builder()
//                .id("invoiceId")
//                .build();
//
//        when(invoiceRepository.findById("invoiceId")).thenReturn(Optional.of(invoice));
//
//        InvoiceDTO result = invoiceService.getInvoiceById("invoiceId");
//
//        assertEquals(invoice.getId(), result.getId());
//    }
//
//    @Test
//    void testGetInvoiceById_NotFound() {
//        when(invoiceRepository.findById("invoiceId")).thenReturn(Optional.empty());
//
//        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
//            invoiceService.getInvoiceById("invoiceId");
//        });
//
//        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
//        assertEquals("Invoice not found with id: invoiceId", thrown.getReason());
//    }
//
//    @Test
//    void testGetAllInvoices() {
//        List<Invoice> invoices = Arrays.asList(
//                Invoice.builder().id("invoice1").build(),
//                Invoice.builder().id("invoice2").build()
//        );
//
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Invoice> invoicePage = new org.springframework.data.domain.PageImpl<>(invoices, pageable, invoices.size());
//
//        when(invoiceRepository.findAll(pageable)).thenReturn(invoicePage);
//
//        List<InvoiceDTO> result = invoiceService.getAllInvoices(0, 10);
//
//        assertEquals(2, result.size());
//        assertEquals("invoice1", result.get(0).getId());
//        assertEquals("invoice2", result.get(1).getId());
//    }
//
//    @Test
//    void testExportAllInvoicesToPDF_Success() throws IOException {
//        byte[] pdfContent = new byte[]{1, 2, 3};
//
//        when(pdfUtils.generateAllInvoicesPDF(anyList())).thenReturn(pdfContent);
//
//        byte[] result = invoiceService.exportAllInvoicesToPDF();
//
//        assertArrayEquals(pdfContent, result);
//    }
//
//    @Test
//    void testGetRevenueByPeriod() {
//        List<Invoice> invoices = Arrays.asList(
//                Invoice.builder()
//                        .invoiceDate(Timestamp.valueOf("2024-08-01 00:00:00"))
//                        .invoiceAmount(BigDecimal.valueOf(100))
//                        .build(),
//                Invoice.builder()
//                        .invoiceDate(Timestamp.valueOf("2024-08-02 00:00:00"))
//                        .invoiceAmount(BigDecimal.valueOf(200))
//                        .build()
//        );
//
//        when(invoiceRepository.findByInvoiceDateBetween(any(Timestamp.class), any(Timestamp.class))).thenReturn(invoices);
//
//        List<RevenueReportDTO> result = invoiceService.getRevenueByPeriod(2024, null, null);
//
//        assertEquals(2, result.size());
//        assertEquals(LocalDate.parse("2024-08-01"), result.get(0).getDate());
//        assertEquals(BigDecimal.valueOf(100), result.get(0).getRevenue());
//        assertEquals(LocalDate.parse("2024-08-02"), result.get(1).getDate());
//        assertEquals(BigDecimal.valueOf(200), result.get(1).getRevenue());
//    }
//}
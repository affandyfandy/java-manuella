package com.fpt.midterm_g1.data.repository;

import com.fpt.midterm_g1.common.Status;
import com.fpt.midterm_g1.data.entity.Customer;
import com.fpt.midterm_g1.data.entity.Invoice;
import com.fpt.midterm_g1.data.entity.InvoiceProduct;
import com.fpt.midterm_g1.data.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class InvoiceProductRepositoryTest {

    @Autowired
    private InvoiceProductRepository invoiceProductRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;
    private Product product;
    private Invoice invoice;
    private InvoiceProduct invoiceProduct;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .name("John Doe")
                .phoneNumber("123456789")
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        product = Product.builder()
                .name("Product A")
                .price(BigDecimal.valueOf(100.0))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        invoice = Invoice.builder()
                .customer(customer)
                .invoiceAmount(BigDecimal.valueOf(100.0))
                .invoiceDate(new Timestamp(System.currentTimeMillis()))
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        customerRepository.save(customer);
        productRepository.save(product);
        invoice = invoiceRepository.save(invoice);

        invoiceProduct = InvoiceProduct.builder()
                .invoice_id(invoice.getId())
                .product_id(product.getId())
                .invoice(invoice)
                .product(product)
                .quantity(1)
                .price(BigDecimal.valueOf(100.0))
                .amount(BigDecimal.valueOf(100.0))
                .build();

        invoiceProductRepository.save(invoiceProduct);
    }

    @Test
    @Transactional
    void testDeleteByInvoice() {
        invoiceProductRepository.deleteByInvoice(invoice);

        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAll();
        assertThat(invoiceProducts).isEmpty();
    }

    @Test
    void testFindAllWithDetails() {
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllWithDetails();

        assertThat(invoiceProducts).isNotEmpty();
        assertThat(invoiceProducts.get(0).getInvoice()).isNotNull();
        assertThat(invoiceProducts.get(0).getProduct()).isNotNull();
    }
}
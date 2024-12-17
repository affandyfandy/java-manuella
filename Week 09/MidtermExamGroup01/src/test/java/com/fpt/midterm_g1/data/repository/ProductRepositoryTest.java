package com.fpt.midterm_g1.data.repository;

import com.fpt.midterm_g1.common.Status;
import com.fpt.midterm_g1.data.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = Product.builder()
                .name("Product A")
                .price(BigDecimal.valueOf(100.0))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        product2 = Product.builder()
                .name("Product B")
                .price(BigDecimal.valueOf(200.0))
                .status(Status.INACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    void testFindById() {
        Product fetchedProduct = productRepository.findById(product1.getId()).orElse(null);
        assertThat(fetchedProduct).isNotNull();
        assertThat(fetchedProduct.getName()).isEqualTo("Product A");
    }

    @Test
    void testFindAll() {
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void testAdd() {
        Product newProduct = Product.builder()
                .name("Product C")
                .price(BigDecimal.valueOf(150.0))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        Product savedProduct = productRepository.save(newProduct);
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Product C");
    }

    @Test
    void testEdit() {
        product1.setName("Updated Product A");
        productRepository.save(product1);

        Product updatedProduct = productRepository.findById(product1.getId()).orElse(null);
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getName()).isEqualTo("Updated Product A");
    }

    @Test
    void testActivate() {
        product2.setStatus(Status.ACTIVE);
        productRepository.save(product2);

        Product updatedProduct = productRepository.findById(product2.getId()).orElse(null);
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getStatus()).isEqualTo(Status.ACTIVE);
    }

    @Test
    void testDeactivate() {
        product1.setStatus(Status.INACTIVE);
        productRepository.save(product1);

        Product updatedProduct = productRepository.findById(product1.getId()).orElse(null);
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getStatus()).isEqualTo(Status.INACTIVE);
    }

    @Test
    void testFindByNameContaining() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = productRepository.findByNameContaining("Product", pageable);

        assertThat(page).isNotNull();
        assertThat(page.getContent()).hasSize(2);
    }

    @Test
    void testFindByStatus() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = productRepository.findByStatus(Status.ACTIVE, pageable);

        assertThat(page).isNotNull();
        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getStatus()).isEqualTo(Status.ACTIVE);
    }

    @Test
    void testFindByNameContainingAndStatus() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = productRepository.findByNameContainingAndStatus("Product", Status.ACTIVE, pageable);

        assertThat(page).isNotNull();
        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getStatus()).isEqualTo(Status.ACTIVE);
    }
}

package com.fpt.MidtermG1.data.repository;

import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.data.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindByNameContaining() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(new BigDecimal("100.00"));
        product.setStatus(Status.ACTIVE);
        productRepository.save(product);

        Page<Product> result = productRepository.findByNameContaining("Test", PageRequest.of(0, 10));

        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Test Product");
    }

    @Test
    public void testFindByStatus() {
        Product product = new Product();
        product.setName("Another Product");
        product.setPrice(new BigDecimal("150.00"));
        product.setStatus(Status.INACTIVE);
        productRepository.save(product);

        Page<Product> result = productRepository.findByStatus(Status.INACTIVE, PageRequest.of(0, 10));

        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0).getStatus()).isEqualTo(Status.INACTIVE);
    }
}

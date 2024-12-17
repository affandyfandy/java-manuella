package com.fpt.midterm_g1.data.entity;

import com.fpt.midterm_g1.common.Status;
import com.fpt.midterm_g1.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;

class ProductTest {
    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1)
                .name("Product A")
                .price(new BigDecimal("99.99"))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    @Test
    void testProductEntity() {
        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(1);
        assertThat(product.getName()).isEqualTo("Product A");
        assertThat(product.getPrice()).isEqualByComparingTo(new BigDecimal("99.99"));
        assertThat(product.getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(product.getCreatedTime()).isNotNull();
        assertThat(product.getUpdatedTime()).isNotNull();
    }

    @Test
    void testProductDTOConversion() {
        ProductDTO dto = product.toDTO();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(product.getId());
        assertThat(dto.getName()).isEqualTo(product.getName());
        assertThat(dto.getPrice()).isEqualByComparingTo(product.getPrice());
        assertThat(dto.getStatus()).isEqualTo(product.getStatus());
        assertThat(dto.getCreatedTime()).isEqualTo(product.getCreatedTime());
        assertThat(dto.getUpdatedTime()).isEqualTo(product.getUpdatedTime());
    }

    @Test
    void testProductPrePersist() {
        Product newProduct = new Product();
        newProduct.onCreate();
        assertThat(newProduct.getCreatedTime()).isNotNull();
        assertThat(newProduct.getUpdatedTime()).isNotNull();
    }

    @Test
    void testProductPreUpdate() {
        Timestamp initialUpdatedTime = product.getUpdatedTime();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        product.preUpdate();
        Timestamp updatedTimeAfterUpdate = product.getUpdatedTime();
        assertThat(updatedTimeAfterUpdate).isAfter(initialUpdatedTime);
    }
}
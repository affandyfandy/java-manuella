package com.fpt.midterm_g1.dto;

import com.fpt.midterm_g1.common.Status;
import com.fpt.midterm_g1.data.entity.Product;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductDTOTest {
    @Test
    public void testProductToDTO() {
        Product product = new Product();
        product.setId(1);
        product.setName("Test Product");
        product.setPrice(new BigDecimal("100.00"));
        product.setStatus(Status.ACTIVE);
        product.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        product.setUpdatedTime(new Timestamp(System.currentTimeMillis()));

        ProductDTO productDTO = product.toDTO();

        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getPrice(), productDTO.getPrice());
        assertEquals(product.getStatus(), productDTO.getStatus());
        assertEquals(product.getCreatedTime(), productDTO.getCreatedTime());
        assertEquals(product.getUpdatedTime(), productDTO.getUpdatedTime());
    }
}
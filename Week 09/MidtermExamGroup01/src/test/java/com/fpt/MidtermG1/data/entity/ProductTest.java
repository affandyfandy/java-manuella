package com.fpt.MidtermG1.data.entity;

import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.dto.ProductDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    @Test
    public void testToDTO(){
        Product product = Product.builder()
                .id(1)
                .name("Test Product")
                .price(new BigDecimal("100.00"))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        ProductDTO productDTO = product.toDTO();
        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getPrice(), productDTO.getPrice());
        assertEquals(product.getStatus(), productDTO.getStatus());
    }
}

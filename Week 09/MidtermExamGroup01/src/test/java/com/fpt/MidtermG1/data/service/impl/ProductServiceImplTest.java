package com.fpt.MidtermG1.data.service.impl;

import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.data.entity.Product;
import com.fpt.MidtermG1.data.repository.ProductRepository;
import com.fpt.MidtermG1.dto.ProductDTO;
import com.fpt.MidtermG1.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testListAllProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Test Product");
        product.setPrice(new BigDecimal("100.00"));
        product.setStatus(Status.ACTIVE);

        Page<Product> page = new PageImpl<>(Collections.singletonList(product), PageRequest.of(0, 10), 1);

        when(productRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<ProductDTO> result = productService.listAllProduct(PageRequest.of(0, 10), "");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }


    @Test
    public void testFindProductById() {
        Product product = new Product();
        product.setId(1);
        product.setName("Test Product");
        product.setPrice(new BigDecimal("100.00"));
        product.setStatus(Status.ACTIVE);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Optional<ProductDTO> result = productService.findProductById(1);

        assertTrue(result.isPresent());
        assertEquals("Test Product", result.get().getName());
    }

    @Test
    public void testSaveProduct() {
        ProductDTO dto = ProductDTO.builder()
                .name("Test Product")
                .price(new BigDecimal("100.00"))
                .status(Status.ACTIVE)
                .build();
        Product product = dto.toEntity();
        when(productRepository.save(product)).thenReturn(product);

        ProductDTO result = productService.saveProduct(dto);

        assertNotNull(result);
        assertEquals("Test Product", result.getName());
    }

    @Test
    public void testUpdateProduct() {
        ProductDTO dto = ProductDTO.builder()
                .name("Updated Product")
                .price(new BigDecimal("150.00"))
                .status(Status.INACTIVE)
                .build();
        Product product = dto.toEntity();
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        Optional<ProductDTO> result = productService.updateProduct(1, dto);

        assertTrue(result.isPresent());
        assertEquals("Updated Product", result.get().getName());
    }

    @Test
    public void testActivateProduct() {
        Product product = new Product();
        product.setId(1);
        product.setStatus(Status.INACTIVE);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        ProductDTO result = productService.activateProduct(1);

        assertEquals(Status.ACTIVE, result.getStatus());
    }

    @Test
    public void testDeactivateProduct() {
        Product product = new Product();
        product.setId(1);
        product.setStatus(Status.ACTIVE);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        ProductDTO result = productService.deactivateProduct(1);

        assertEquals(Status.INACTIVE, result.getStatus());
    }

}

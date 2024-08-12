package com.fpt.midterm_g1.service.impl;

import com.fpt.midterm_g1.common.Status;
import com.fpt.midterm_g1.data.entity.Product;
import com.fpt.midterm_g1.data.repository.ProductRepository;
import com.fpt.midterm_g1.dto.ProductDTO;
import com.fpt.midterm_g1.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ProductServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAllProduct() {
        Pageable pageable = Pageable.unpaged();
        Product product = Product.builder()
                .id(1)
                .name("Product A")
                .price(BigDecimal.valueOf(100.0))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        Page<Product> productsPage = new PageImpl<>(Collections.singletonList(product));
        when(productRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(productsPage);

        Page<ProductDTO> result = productService.listAllProduct(pageable, "name:Product A");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Product A", result.getContent().get(0).getName());
    }


    @Test
    void testFindProductById() {
        Product product = Product.builder()
                .id(1)
                .name("Product A")
                .price(BigDecimal.valueOf(100.0))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Optional<ProductDTO> result = productService.findProductById(1);

        assertTrue(result.isPresent());
        assertEquals("Product A", result.get().getName());
    }

    @Test
    void testFindProductByIdNotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.findProductById(1);
        });

        assertEquals("Product not found for this id : 1", exception.getMessage());
    }

    @Test
    void testSaveProduct() {
        ProductDTO productDTO = ProductDTO.builder()
                .name("Product A")
                .price(BigDecimal.valueOf(100.0))
                .status(Status.ACTIVE)
                .build();

        Product product = productDTO.toEntity();
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO result = productService.saveProduct(productDTO);

        assertNotNull(result);
        assertEquals("Product A", result.getName());
    }

    @Test
    void testUpdateProduct() {
        Product existingProduct = Product.builder()
                .id(1)
                .name("Product A")
                .price(BigDecimal.valueOf(100.0))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        ProductDTO productDTO = ProductDTO.builder()
                .name("Updated Product")
                .price(BigDecimal.valueOf(150.0))
                .status(Status.INACTIVE)
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Optional<ProductDTO> result = productService.updateProduct(1, productDTO);

        assertTrue(result.isPresent());
        assertEquals("Updated Product", result.get().getName());
        assertEquals(BigDecimal.valueOf(150.0), result.get().getPrice());
        assertEquals(Status.INACTIVE, result.get().getStatus());
    }

    @Test
    void testUpdateProductNotFound() {
        ProductDTO productDTO = ProductDTO.builder()
                .name("Updated Product")
                .price(BigDecimal.valueOf(150.0))
                .status(Status.INACTIVE)
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(1, productDTO);
        });

        assertEquals("Product not found for this id : 1", exception.getMessage());
    }

    @Test
    void testImportExcel() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Dummy.xlsx");
        if (inputStream == null) {
            throw new RuntimeException("File not found in test resources.");
        }

        List<Product> mockProducts = List.of(
                Product.builder().name("Apple iPhone 14").price(new BigDecimal("99999")).status(Status.ACTIVE).build(),
                Product.builder().name("Samsung Galaxy S23").price(new BigDecimal("94999")).status(Status.INACTIVE).build(),
                Product.builder().name("Sony WH-1000XM5 Headphones").price(new BigDecimal("34999")).status(Status.ACTIVE).build()
        );

        // Mock the saveAll call
        when(productRepository.saveAll(anyList())).thenReturn(mockProducts);

        productService.importExcel(inputStream);

        ArgumentCaptor<List<Product>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(productRepository).saveAll(argumentCaptor.capture());

        List<Product> capturedProducts = argumentCaptor.getValue();

        assertEquals(3, capturedProducts.size(), "Number of products saved should be 3");

        List<String> names = capturedProducts.stream().map(Product::getName).toList();
        assertTrue(names.containsAll(List.of("Apple iPhone 14", "Samsung Galaxy S23", "Sony WH-1000XM5 Headphones")), "Product names do not match");
    }


    @Test
    void testActivateProduct() {
        Product product = Product.builder()
                .id(1)
                .name("Product A")
                .price(BigDecimal.valueOf(100.0))
                .status(Status.INACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        ProductDTO result = productService.activateProduct(1);

        assertNotNull(result);
        assertEquals(Status.ACTIVE, result.getStatus());
    }

    @Test
    void testActivateProductAlreadyActive() {
        Product product = Product.builder()
                .id(1)
                .name("Product A")
                .price(BigDecimal.valueOf(100.0))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.activateProduct(1);
        });

        assertEquals("Product status already ACTIVE", exception.getMessage());
    }

    @Test
    void testDeactivateProduct() {
        Product product = Product.builder()
                .id(1)
                .name("Product A")
                .price(BigDecimal.valueOf(100.0))
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        ProductDTO result = productService.deactivateProduct(1);

        assertNotNull(result);
        assertEquals(Status.INACTIVE, result.getStatus());
    }

    @Test
    void testDeactivateProductAlreadyInactive() {
        Product product = Product.builder()
                .id(1)
                .name("Product A")
                .price(BigDecimal.valueOf(100.0))
                .status(Status.INACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.deactivateProduct(1);
        });

        assertEquals("Product status already INACTIVE", exception.getMessage());
    }
}

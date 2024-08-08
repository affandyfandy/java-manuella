//package com.fpt.MidtermG1.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fpt.MidtermG1.common.Status;
//import com.fpt.MidtermG1.dto.ProductDTO;
//import com.fpt.MidtermG1.service.ProductService;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.data.domain.Page;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(ProductController.class)
//@RequiredArgsConstructor
//public class ProductControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private ProductService productService;
//
//    @InjectMocks
//    private ProductController productController;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
//    }
//
//    @Test
//    public void testGetAllProducts() throws Exception {
//        when(productService.listAllProduct(any(), any())).thenReturn(Page.empty());
//
//        mockMvc.perform(get("/api/v1/products"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.content").isEmpty());
//    }
//
//    @Test
//    public void testAddProduct() throws Exception {
//        ProductDTO dto = ProductDTO.builder()
//                .name("New Product")
//                .price(new BigDecimal("200.00"))
//                .status(Status.ACTIVE)
//                .build();
//        when(productService.saveProduct(any(ProductDTO.class))).thenReturn(dto);
//
//        mockMvc.perform(post("/api/v1/products")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(dto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value("New Product"))
//                .andExpect(jsonPath("$.price").value(200.00))
//                .andExpect(jsonPath("$.status").value("ACTIVE"));
//    }
//
//    @Test
//    public void testEditProduct() throws Exception {
//        ProductDTO dto = ProductDTO.builder()
//                .name("Updated Product")
//                .price(new BigDecimal("250.00"))
//                .status(Status.ACTIVE)
//                .build();
//        when(productService.updateProduct(anyInt(), any(ProductDTO.class))).thenReturn(Optional.of(dto));
//
//        mockMvc.perform(put("/api/v1/products/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Updated Product"))
//                .andExpect(jsonPath("$.price").value(250.00))
//                .andExpect(jsonPath("$.status").value("ACTIVE"));
//    }
//
//    @Test
//    public void testImportProducts() throws Exception {
//        mockMvc.perform(multipart("/api/v1/products/import")
//                        .file("file", new byte[0])) // Simulate file upload
//                .andExpect(status().isOk())
//                .andExpect(content().string("Products imported successfully"));
//    }
//
//    @Test
//    public void testActivateProduct() throws Exception {
//        ProductDTO dto = ProductDTO.builder()
//                .id(1)
//                .status(Status.ACTIVE)
//                .build();
//        when(productService.activateProduct(anyInt())).thenReturn(dto);
//
//        mockMvc.perform(put("/api/v1/products/activate/{id}", 1))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("ACTIVE"));
//    }
//
//    @Test
//    public void testDeactivateProduct() throws Exception {
//        ProductDTO dto = ProductDTO.builder()
//                .id(1)
//                .status(Status.INACTIVE)
//                .build();
//        when(productService.deactivateProduct(anyInt())).thenReturn(dto);
//
//        mockMvc.perform(put("/api/v1/products/deactivate/{id}", 1))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("INACTIVE"));
//    }
//}

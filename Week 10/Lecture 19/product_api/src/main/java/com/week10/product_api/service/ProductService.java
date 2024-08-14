package com.week10.product_api.service;

import com.week10.product_api.exception.ResourceNotFoundException;
import com.week10.product_api.model.Product;
import com.week10.product_api.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()){
            throw new ResourceNotFoundException("Product not found for this id : " + id);
        }
        return productOptional.get();
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product){
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()){
            throw new ResourceNotFoundException("Product not found for this id : " + id);
        } else{
            Product saveProduct = productOptional.get();
            saveProduct.setName(product.getName());
            return productRepository.save(saveProduct);
        }

    }

    public void deleteProduct(Long id){
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()){
            throw new ResourceNotFoundException("Product not found for this id : " + id);
        } else{
            productRepository.delete(productOptional.get());
        }
    }
}

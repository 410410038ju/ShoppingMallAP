package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Product;
import com.example.demo.model.ProductCategory;
import com.example.demo.msg.ProductCategoryMsg;
import com.example.demo.repository.ProductCategoryRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public Product saveProduct(Product product, Long categoryId) {

        // 取得分類，找不到就回傳 null（或你等下要改成 throw NotFoundException）
        ProductCategory category = productCategoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new NotFoundException(
                                ProductCategoryMsg.NOT_FOUND.getCode(),
                                ProductCategoryMsg.NOT_FOUND.getMsg()
                        )
                );

        product.setCategory(category);

        return productRepository.save(product);
    }


    public boolean deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return true;
        } else {
            return false;
        }
    }
}

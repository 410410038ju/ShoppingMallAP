package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.dto.request.CreateProductRequest;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "商品管理")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    @GetMapping("/common/products")
    @Operation(summary = "取得所有商品")
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    @Operation(summary = "根據ID取得商品資料")
    @GetMapping("/common/products/{productId}")
    public Optional<Product> getById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @Operation(summary = "新增商品")
    @PostMapping("/employee/products")
    public Product create(@RequestBody CreateProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .status(Product.Status.valueOf(request.getStatus()))
                .description(request.getDescription())
                .category(request.getCategory())
                .imageUrl(request.getImageUrl())
                .build();

        return productService.saveProduct(product);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @Operation(summary = "更新商品")
    @PutMapping("/employee/products/{productId}")
    public Product update(
            @PathVariable Long productId,
            @Valid @RequestBody CreateProductRequest request) {

        Optional<Product> optionalProduct = productService.getProductById(productId);

        if(optionalProduct.isEmpty()) {
            throw new RuntimeException("商品不存在");
        }

//        if (optionalProduct.isEmpty()) {
//            throw new ResourceNotFoundException("找不到指定的商品 ID: " + productId);
//        }


        Product product = optionalProduct.get();

        // 將可更新欄位從 request 複製到 entity
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setStatus(Product.Status.valueOf(request.getStatus()));
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());

        return productService.saveProduct(product);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @Operation(summary = "刪除商品")
    @DeleteMapping("/employee/products/{productId}")
    public void delete(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}

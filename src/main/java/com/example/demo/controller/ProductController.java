package com.example.demo.controller;

import com.example.demo.constant.ProductStatus;
import com.example.demo.model.Product;
import com.example.demo.dto.request.CreateProductRequest;
import com.example.demo.model.ProductCategory;
import com.example.demo.msg.ProductCategoryMsg;
import com.example.demo.service.ProductCategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.utils.SvcResModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ProductCategoryService productCategoryService;

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    @GetMapping("/common/products")
    @Operation(summary = "取得所有商品")
    public SvcResModel<List<Product>> getAll() {
        List<Product> products = productService.getAllProducts();
        return SvcResModel.success("取得所有商品成功", products);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CUSTOMER')")
    @Operation(summary = "根據ID取得商品資料")
    @GetMapping("/common/products/{productId}")
    public ResponseEntity<SvcResModel<Product>> getById(@PathVariable Long productId) {
        return productService.getProductById(productId)
                .map(product -> ResponseEntity.ok(SvcResModel.success("取得商品成功", product)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(SvcResModel.error("找不到指定商品 ID: " + productId)));
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @Operation(summary = "新增商品")
    @PostMapping("/employee/products")
    public ResponseEntity<SvcResModel<Product>> create(@Valid @RequestBody CreateProductRequest request) {

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .status(ProductStatus.getEnum(request.getStatus()))
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .build();

        Product saved = productService.saveProduct(product, request.getCategoryId());
        return ResponseEntity.ok(SvcResModel.success("新增商品成功", saved));
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @Operation(summary = "更新商品")
    @PutMapping("/employee/products/{productId}")
    public ResponseEntity<SvcResModel<Product>> update(
            @PathVariable Long productId,
            @Valid @RequestBody CreateProductRequest request) {

        return productService.getProductById(productId)
                .map(existing -> {
                    existing.setName(request.getName());
                    existing.setPrice(request.getPrice());
                    existing.setStockQuantity(request.getStockQuantity());
                    existing.setStatus(ProductStatus.getEnum(request.getStatus()));
                    existing.setDescription(request.getDescription());
                    existing.setImageUrl(request.getImageUrl());

                    Product updated = productService.saveProduct(existing, request.getCategoryId());
                    return ResponseEntity.ok(SvcResModel.success("更新商品成功", updated));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(SvcResModel.error("找不到指定商品 ID: " + productId)));
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @Operation(summary = "刪除商品")
    @DeleteMapping("/employee/products/{productId}")
    public ResponseEntity<SvcResModel<Object>> delete(@PathVariable Long productId) {
        boolean deleted = productService.deleteProduct(productId);
        if (deleted) {
            return ResponseEntity.ok(SvcResModel.success("刪除商品成功", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(SvcResModel.error("找不到指定商品 ID: " + productId));
        }
    }
}

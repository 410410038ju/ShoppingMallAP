package com.example.demo.controller;

import com.example.demo.model.ProductCategory;
import com.example.demo.service.ProductCategoryService;
import com.example.demo.utils.SvcResModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
@RequiredArgsConstructor
@Tag(name = "商品類別管理")
public class ProductCategoryController {

    private final ProductCategoryService service;

    @Operation(summary = "取得所有商品類別")
    @GetMapping
    public ResponseEntity<SvcResModel<List<ProductCategory>>> getAll() {
        List<ProductCategory> list = service.getAll();
        return ResponseEntity.ok(SvcResModel.success(list));
    }

    @Operation(summary = "依ID取得商品類別")
    @GetMapping("/{id}")
    public ResponseEntity<SvcResModel<ProductCategory>> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(pc -> ResponseEntity.ok(SvcResModel.success(pc)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(SvcResModel.<ProductCategory>error("查無商品類別")));
    }

    @Operation(summary = "新增商品類別")
    @PostMapping
    public ResponseEntity<SvcResModel<ProductCategory>> create(@RequestBody ProductCategory category) {
        ProductCategory saved = service.create(category);
        return ResponseEntity.ok(SvcResModel.success(saved));
    }

    @Operation(summary = "更新商品類別")
    @PutMapping("/{id}")
    public ResponseEntity<SvcResModel<ProductCategory>> update(@PathVariable Long id,
                                                               @RequestBody ProductCategory category) {
        ProductCategory updated = service.update(id, category);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(SvcResModel.<ProductCategory>error("查無商品類別"));
        }
        return ResponseEntity.ok(SvcResModel.success(updated));
    }

    @Operation(summary = "刪除商品類別")
    @DeleteMapping("/{id}")
    public ResponseEntity<SvcResModel<Object>> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(SvcResModel.<Object>error("查無商品類別"));
        }
        return ResponseEntity.ok(SvcResModel.success("刪除成功", null));
    }
}

package com.example.demo.controller;

import com.example.demo.model.ProductCategory;
import com.example.demo.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService service;

    @GetMapping
    public List<ProductCategory> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ProductCategory getById(@PathVariable Long id) {
        return service.getById(id).orElse(null);
    }

    @PostMapping
    public ProductCategory create(@RequestBody ProductCategory category) {
        return service.create(category);
    }

    @PutMapping("/{id}")
    public ProductCategory update(@PathVariable Long id, @RequestBody ProductCategory category) {
        return service.update(id, category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

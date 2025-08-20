package com.example.demo.service;

import com.example.demo.model.ProductCategory;
import com.example.demo.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    public List<ProductCategory> getAll() {
        return repository.findAll();
    }

    public Optional<ProductCategory> getById(Long id) {
        return repository.findById(id);
    }

    public ProductCategory create(ProductCategory category) {
        return repository.save(category);
    }

    public ProductCategory update(Long id, ProductCategory category) {
        category.setId(id);
        return repository.save(category);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

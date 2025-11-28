package com.example.demo.service;

import com.example.demo.model.OrderDetailId;
import com.example.demo.model.ProductCategory;
import com.example.demo.repository.ProductCategoryRepository;
import com.example.demo.dto.request.CreateProductCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository repository;

    public List<ProductCategory> getAll() {
        return repository.findAll();
    }

    public Optional<ProductCategory> getById(Long id) {
        return repository.findById(id);
    }

    // 新增商品類別
    public ProductCategory create(CreateProductCategoryRequest request) {
        ProductCategory category = new ProductCategory();
        category.setName(request.getName());
        return repository.save(category);
    }

    public ProductCategory update(Long id, ProductCategory category) {
        category.setId(id);
        return repository.save(category);
    }

   public boolean delete(Long id) {
       return repository.findById(id)
               .map(existing -> {
                   repository.deleteById(id);
                   return true;
               }).orElse(false);
   }

}

package com.example.demo.service;

import com.example.demo.model.OrderDetailId;
import com.example.demo.model.ProductCategory;
import com.example.demo.repository.ProductCategoryRepository;
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

    public ProductCategory create(ProductCategory category) {
        return repository.save(category);
    }

    public ProductCategory update(Long id, ProductCategory category) {
        category.setId(id);
        return repository.save(category);
    }

   /*public void delete(Long id) {
        repository.deleteById(id);
    }*/
   public boolean delete(Long id) {
       return repository.findById(id)
               .map(existing -> {
                   repository.deleteById(id);
                   return true;
               }).orElse(false);
   }

}

package com.example.demo.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    private String name;
    private BigDecimal price;
    private Integer stockQuantity;
    private String status;
    private String description;
    private String category;
    private String imageUrl;
}

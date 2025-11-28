package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductCategoryRequest {

    @NotBlank(message = "商品類別名稱不能為空")
    private String name;
}

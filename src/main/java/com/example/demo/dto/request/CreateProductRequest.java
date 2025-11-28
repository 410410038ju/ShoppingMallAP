package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    @NotBlank(message = "商品名稱不能為空")
    private String name;

    @NotNull(message = "商品價格不能為空")
    private BigDecimal price;

    @NotNull(message = "庫存數量不能為空")
    private Integer stockQuantity;

    @NotBlank(message = "商品狀態不能為空")
    private String status;

    private String description;

    @NotNull(message = "商品類別 ID 不能為空")
    private Long categoryId;

    private String imageUrl;
}

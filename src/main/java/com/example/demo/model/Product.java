package com.example.demo.model;

import com.example.demo.constant.ProductStatus;
import com.example.demo.constant.ProductStatusConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId; // 商品編號

    @NotBlank
    private String name; // 商品名稱

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price; // 單價

    @NotNull
    @Min(0)
    private Integer stockQuantity; // 商品庫存數量
/*
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status; // 商品狀態
    */

    @NotNull
    @Convert(converter = ProductStatusConverter.class)
    private ProductStatus status; // 商品狀態

    @Size(max = 500)
    private String description; // 商品描述

    @NotBlank
    private String category; // 商品類別

    @Size(max = 255)
    private String imageUrl; // 圖片網址

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;
/*
    public enum Status {
        上架, 下架
    }*/

    @CreationTimestamp
    private LocalDateTime createdAt;  // 新增資料時自動記錄時間

    @UpdateTimestamp
    private LocalDateTime updatedAt;  // 每次資料更新時自動記錄時間
}

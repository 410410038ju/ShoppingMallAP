package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId; // 客戶ID

    private String name; // 客戶姓名
    private String password; // 密碼
    private String email; //墊子郵件
    private String phone; // 手機號碼
    private String address; // 地址

    @CreationTimestamp
    private LocalDateTime createdAt;  // 新增資料時自動記錄時間

    @UpdateTimestamp
    private LocalDateTime updatedAt;  // 每次資料更新時自動記錄時間

    @OneToMany(mappedBy = "customer") // 一位客戶可以有多筆訂單
    @JsonIgnore
    private List<Order> orders; // 該客戶擁有的訂單清單


}

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
@Table(name = "employee")
public class Employee {

    @Id // 主鍵
    private Long employeeId;  // 員工編號

    private String name;  // 員工姓名
    private String password; // 登入密碼

    @CreationTimestamp
    private LocalDateTime createdAt;  // 新增資料時自動記錄時間

    @UpdateTimestamp
    private LocalDateTime updatedAt;  // 每次資料更新時自動記錄時間

    @OneToMany(mappedBy = "employee") // 一位員工對多筆訂單（Order）
    @JsonIgnore
    private List<Order> orders; // 關聯到此員工所負責的訂單清單

}

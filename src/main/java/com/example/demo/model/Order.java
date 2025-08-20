package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`order`") // 避免與 SQL 關鍵字衝突
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer; // 顧客編號

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee; // 負責員工編號

    private LocalDate orderDate; // 訂單日期

    private BigDecimal totalAmount; // 總金額

    private String paymentMethod; // 付款方式

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // 付款請況

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 訂單狀態

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
//    private List<OrderDetail> orderDetails;
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public enum PaymentStatus {
        未付款, 已付款
    }

    public enum OrderStatus {
        處理中, 已出貨, 交易完成, 交易失敗, 交易取消
    }

    public void addOrderDetail(OrderDetail detail) {
        if (this.orderDetails == null) this.orderDetails = new ArrayList<>();
        detail.setOrder(this);
        this.orderDetails.add(detail);
    }

    public List<OrderDetail> getOrderDetails() {
        if (this.orderDetails == null) this.orderDetails = new ArrayList<>();
        return this.orderDetails;
    }
}

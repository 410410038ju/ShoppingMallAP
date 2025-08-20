package com.example.demo.repository;

import com.example.demo.model.Order;
import com.example.demo.model.Order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
/*
public interface OrderRepository extends JpaRepository<Order, Long> {

    // 多條件查詢
    List<Order> findByCustomerCustomerIdAndEmployeeEmployeeIdAndOrderDateBetweenAndOrderStatusAndPaymentStatus(
            Long customerId,
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate,
            Order.OrderStatus orderStatus,
            Order.PaymentStatus paymentStatus
    );

}*/

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
}


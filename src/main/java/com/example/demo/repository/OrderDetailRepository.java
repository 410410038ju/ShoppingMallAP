package com.example.demo.repository;

import com.example.demo.model.OrderDetail;
import com.example.demo.model.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findByOrderOrderId(Long orderId);
}
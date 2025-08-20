package com.example.demo.dto.request;

import com.example.demo.model.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateOrderRequest {
    private Long customerId;
    private Long employeeId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private Order.PaymentStatus paymentStatus;
    private Order.OrderStatus orderStatus;
    private List<UpdateOrderDetailRequest> orderDetails;
}

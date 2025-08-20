package com.example.demo.dto.request;

import com.example.demo.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private Long customerId;
    private Long employeeId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private Order.PaymentStatus paymentStatus;
    private Order.OrderStatus orderStatus;

    @Schema(description = "訂單明細資料")
    private List<CreateOrderDetailRequest> orderDetails;
}

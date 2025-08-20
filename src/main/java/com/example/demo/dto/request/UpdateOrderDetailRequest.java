package com.example.demo.dto.request;

import lombok.Data;

@Data
public class UpdateOrderDetailRequest {
    private Long orderId;
    private Long productId;
    private Integer quantity;
}

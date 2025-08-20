package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {

    @EmbeddedId
    @Builder.Default
    private OrderDetailId id = new OrderDetailId();

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    /**
     * 設定 Order 同時保護 id
     */
    public void setOrder(Order order) {
        this.order = order;
        if (this.id == null) {
            this.id = new OrderDetailId();
        }
        if (order != null && order.getOrderId() != null) {
            this.id.setOrderId(order.getOrderId());
        }
    }

    /**
     * 設定 Product 同時保護 id
     */
    public void setProduct(Product product) {
        this.product = product;
        if (this.id == null) {
            this.id = new OrderDetailId();
        }
        if (product != null && product.getProductId() != null) {
            this.id.setProductId(product.getProductId());
        }
    }

}


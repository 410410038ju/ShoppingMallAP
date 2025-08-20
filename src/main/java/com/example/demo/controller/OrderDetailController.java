package com.example.demo.controller;

import com.example.demo.model.OrderDetail;
import com.example.demo.service.OrderDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "訂單商品數量管理")
@RestController
@RequestMapping
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Operation(summary = "根據訂單ID查詢商品數量")
    @GetMapping("/common/order-detail/{orderId}")
    public List<OrderDetail> getDetails(@PathVariable Long orderId) {
        return orderDetailService.getDetailsByOrderId(orderId);
    }

    @Operation(summary = "新增訂單商品數量")
    @PostMapping("/customer/order-detail")
    public OrderDetail addItem(@RequestParam Long orderId,
                               @RequestParam Long productId,
                               @RequestParam int quantity) {
        return orderDetailService.addItemToOrder(orderId, productId, quantity);
    }

    @Operation(summary = "更新訂單商品數量")
    @PutMapping("/customer/order-detail")
    public OrderDetail updateQuantity(@RequestParam Long orderId,
                                      @RequestParam Long productId,
                                      @RequestParam int quantity) {
        return orderDetailService.updateQuantity(orderId, productId, quantity);
    }

    @Operation(summary = "刪除訂單商品數量")
    @DeleteMapping("/customer/order-detail")
    public void removeItem(@RequestParam Long orderId,
                           @RequestParam Long productId) {
        orderDetailService.removeItem(orderId, productId);
    }
}
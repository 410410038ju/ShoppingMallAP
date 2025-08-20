package com.example.demo.controller;

import com.example.demo.dto.request.CreateOrderRequest;
import com.example.demo.dto.request.UpdateOrderRequest;
import com.example.demo.model.*;
import com.example.demo.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Tag(name = "訂單管理")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 取得所有訂單
    @Operation(summary = "取得所有訂單資料")
    @GetMapping("/common/orders")
    public List<Order> getAll() {
        return orderService.getAllOrders();
    }

    // 根據 id 取得訂單
    @Operation(summary = "根據ID取得訂單資料")
    @GetMapping("/common/orders/{orderId}")
    public Optional<Order> getById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    // 新增訂單
    @Operation(summary = "新增訂單")
    @PostMapping("/customer/orders")
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    // 更新訂單
    @Operation(summary = "更新訂單")
    @PutMapping("/common/orders/{orderId}")
    public Order updateOrder(
            @PathVariable Long orderId,
            @RequestBody UpdateOrderRequest request) {

        // 呼叫 Service 的更新方法，回傳更新後的 Order 物件
        return orderService.updateOrder(orderId, request);
    }

    // 刪除訂單
    @Operation(summary = "刪除訂單")
    @DeleteMapping("/customer/orders/{orderId}")
    public void delete(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }

    // 多重查詢訂單
    @Operation(summary = "多重查詢訂單資料")
    @GetMapping("/common/orders/search")
    public List<Order> searchOrders(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Order.OrderStatus orderStatus,
            @RequestParam(required = false) Order.PaymentStatus paymentStatus) {

        LocalDate start = startDate == null ? null : LocalDate.parse(startDate);
        LocalDate end = endDate == null ? null : LocalDate.parse(endDate);

        return orderService.searchOrders(customerId, employeeId, start, end, orderStatus, paymentStatus);
    }
}

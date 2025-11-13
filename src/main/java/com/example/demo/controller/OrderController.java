package com.example.demo.controller;

import com.example.demo.dto.request.CreateOrderRequest;
import com.example.demo.dto.request.UpdateOrderRequest;
import com.example.demo.model.*;
import com.example.demo.service.*;
import com.example.demo.utils.SvcResModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SvcResModel<List<Order>>> getAll() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(SvcResModel.success(orders));
    }

    // 根據 id 取得訂單
    @Operation(summary = "根據ID取得訂單資料")
    @GetMapping("/common/orders/{orderId}")
    public ResponseEntity<SvcResModel<Order>> getById(@PathVariable Long orderId) {
        Optional<Order> orderOpt = orderService.getOrderById(orderId);
        return orderOpt
                .map(order -> ResponseEntity.ok(SvcResModel.success(order)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(SvcResModel.<Order>error("找不到指定的訂單")));
    }

    // 新增訂單
    @Operation(summary = "新增訂單")
    @PostMapping("/customer/orders")
    public ResponseEntity<SvcResModel<Order>> createOrder(@RequestBody CreateOrderRequest request) {
        Order createdOrder = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SvcResModel.success(createdOrder));
    }

    // 更新訂單
    @Operation(summary = "更新訂單")
    @PutMapping("/common/orders/{orderId}")
    public ResponseEntity<SvcResModel<Order>> updateOrder(
            @PathVariable Long orderId,
            @RequestBody UpdateOrderRequest request) {

        Order updatedOrder = orderService.updateOrder(orderId, request);
        if (updatedOrder != null) {
            return ResponseEntity.ok(SvcResModel.success(updatedOrder));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(SvcResModel.<Order>error("找不到指定的訂單，無法更新"));
        }
    }

    // 刪除訂單
    @Operation(summary = "刪除訂單")
    @DeleteMapping("/customer/orders/{orderId}")
    public ResponseEntity<SvcResModel<Object>> delete(@PathVariable Long orderId) {
        boolean deleted = orderService.deleteOrder(orderId);
        if (deleted) {
            return ResponseEntity.ok(SvcResModel.success("刪除成功", null));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(SvcResModel.<Object>error("找不到指定的訂單，無法刪除"));
        }
    }

    // 多重查詢訂單
    @Operation(summary = "多重查詢訂單資料")
    @GetMapping("/common/orders/search")

    public ResponseEntity<SvcResModel<List<Order>>> searchOrders(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Order.OrderStatus orderStatus,
            @RequestParam(required = false) Order.PaymentStatus paymentStatus) {

        LocalDate start = startDate == null ? null : LocalDate.parse(startDate);
        LocalDate end = endDate == null ? null : LocalDate.parse(endDate);

        List<Order> result = orderService.searchOrders(customerId, employeeId, start, end, orderStatus, paymentStatus);
        return ResponseEntity.ok(SvcResModel.success(result));
    }
}

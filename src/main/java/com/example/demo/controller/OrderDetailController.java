package com.example.demo.controller;

import com.example.demo.model.OrderDetail;
import com.example.demo.service.OrderDetailService;
import com.example.demo.utils.SvcResModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "訂單商品數量管理")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Operation(summary = "根據訂單ID查詢商品數量")
    @GetMapping("/common/order-detail/{orderId}")
    /*public ResponseEntity<SvcResModel<List<OrderDetail>>> getDetails(@PathVariable Long orderId) {
        List<OrderDetail> details = orderDetailService.getDetailsByOrderId(orderId);
        if (details.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(SvcResModel.<List<OrderDetail>>error("找不到指定訂單的商品明細"));
        }
        return ResponseEntity.ok(SvcResModel.success(details));
    }*/
    public ResponseEntity<SvcResModel<List<OrderDetail>>> getDetails(@PathVariable Long orderId) {
        List<OrderDetail> details = orderDetailService.getDetailsByOrderId(orderId);
        if (details.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(SvcResModel.<List<OrderDetail>>error("查無此訂單明細"));
        }
        return ResponseEntity.ok(SvcResModel.success(details));
    }

    @Operation(summary = "新增訂單商品數量")
    @PostMapping("/customer/order-detail")
    public ResponseEntity<SvcResModel<OrderDetail>> addItem(@RequestParam Long orderId,
                                                            @RequestParam Long productId,
                                                            @RequestParam int quantity) {
        OrderDetail created = orderDetailService.addItemToOrder(orderId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SvcResModel.success(created));
    }

    @Operation(summary = "更新訂單商品數量")
    @PutMapping("/customer/order-detail")
    public ResponseEntity<SvcResModel<OrderDetail>> updateQuantity(@RequestParam Long orderId,
                                                                   @RequestParam Long productId,
                                                                   @RequestParam int quantity) {
        OrderDetail updated = orderDetailService.updateQuantity(orderId, productId, quantity);
        if (updated == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(SvcResModel.<OrderDetail>error("找不到指定的訂單商品明細，無法更新"));
        }
        return ResponseEntity.ok(SvcResModel.success(updated));
    }

    @Operation(summary = "刪除訂單商品數量")
    @DeleteMapping("/customer/order-detail")
    public ResponseEntity<SvcResModel<Object>> removeItem(@RequestParam Long orderId,
                                                          @RequestParam Long productId) {
        boolean deleted = orderDetailService.removeItem(orderId, productId);
        if (deleted) {
            return ResponseEntity.ok(SvcResModel.success("刪除成功", null));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(SvcResModel.<Object>error("找不到指定的訂單商品明細，無法刪除"));
        }
    }
}
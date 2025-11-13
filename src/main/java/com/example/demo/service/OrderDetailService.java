package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.OrderDetail;
import com.example.demo.model.OrderDetailId;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepo;
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    // 根據訂單ID查詢商品明細
    public List<OrderDetail> getDetailsByOrderId(Long orderId) {
        return orderDetailRepo.findByOrderOrderId(orderId);
    }

    // 新增商品至訂單
    public OrderDetail addItemToOrder(Long orderId, Long productId, int quantity) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("找不到訂單"));
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("找不到商品"));

        OrderDetailId id = new OrderDetailId(orderId, productId);
        OrderDetail detail = new OrderDetail();
        detail.setId(id);
        detail.setOrder(order);
        detail.setProduct(product);
        detail.setQuantity(quantity);

        return orderDetailRepo.save(detail);
    }

    // 更新商品數量
    public OrderDetail updateQuantity(Long orderId, Long productId, int quantity) {
        OrderDetailId id = new OrderDetailId(orderId, productId);
        Optional<OrderDetail> existing = orderDetailRepo.findById(id);
        if (existing.isEmpty()) {
            return null; // 查無資料 → 給 controller 判斷 NOT_FOUND
        }

        OrderDetail detail = existing.get();
        detail.setQuantity(quantity);
        return orderDetailRepo.save(detail);
    }

    // 刪除訂單商品，回傳 boolean 代表成功或失敗
    public boolean removeItem(Long orderId, Long productId) {
        OrderDetailId id = new OrderDetailId(orderId, productId);
        if (orderDetailRepo.existsById(id)) {
            orderDetailRepo.deleteById(id);
            return true;
        }
        return false; // 查無此筆資料
    }
}

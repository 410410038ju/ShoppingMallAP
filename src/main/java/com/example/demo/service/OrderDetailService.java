package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.OrderDetail;
import com.example.demo.model.OrderDetailId;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ProductRepository productRepo;

    public List<OrderDetail> getDetailsByOrderId(Long orderId) {
        return orderDetailRepo.findByOrderOrderId(orderId);
    }

    public OrderDetail addItemToOrder(Long orderId, Long productId, int quantity) {
        Order order = orderRepo.findById(orderId).orElseThrow();
        Product product = productRepo.findById(productId).orElseThrow();

        OrderDetailId id = new OrderDetailId(orderId, productId);
        OrderDetail detail = new OrderDetail();
        detail.setId(id);
        detail.setOrder(order);
        detail.setProduct(product);
        detail.setQuantity(quantity);

        return orderDetailRepo.save(detail);
    }

    public void removeItem(Long orderId, Long productId) {
        OrderDetailId id = new OrderDetailId(orderId, productId);
        orderDetailRepo.deleteById(id);
    }

    public OrderDetail updateQuantity(Long orderId, Long productId, int quantity) {
        OrderDetailId id = new OrderDetailId(orderId, productId);
        OrderDetail detail = orderDetailRepo.findById(id).orElseThrow();
        detail.setQuantity(quantity);
        return orderDetailRepo.save(detail);
    }
}

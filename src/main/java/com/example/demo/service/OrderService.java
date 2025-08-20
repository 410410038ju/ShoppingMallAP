package com.example.demo.service;

import com.example.demo.dto.request.CreateOrderDetailRequest;
import com.example.demo.dto.request.CreateOrderRequest;
import com.example.demo.dto.request.UpdateOrderDetailRequest;
import com.example.demo.dto.request.UpdateOrderRequest;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.specification.OrderSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final ProductService productService;
    private final OrderDetailRepository orderDetailRepository;

    // 取得所有訂單
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 依 id 查詢訂單
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    // 新增訂單
    public Order createOrder(CreateOrderRequest request) {
        // 根據傳入的 customerId 查詢客戶資料
        Customer customer = customerService.getCustomerById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("找不到客戶"));

        // 根據傳入的 employeeId 查詢員工資料
        Employee employee = employeeService.getEmployeeById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("找不到員工"));

        // 建立訂單主體（還沒包含明細）
        Order order = Order.builder()
                .customer(customer)
                .employee(employee)
                .orderDate(request.getOrderDate())
                .totalAmount(request.getTotalAmount())
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(request.getPaymentStatus())
                .orderStatus(request.getOrderStatus())
                .build();

        // 儲存主體 → 此時才會有 order.getId()
        order = orderRepository.save(order);

        // 建立訂單明細列表（每一筆明細對應一個商品和數量）
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CreateOrderDetailRequest detailReq : request.getOrderDetails()) {
            // 根據 productId 查詢商品資料
            Product product = productService.getProductById(detailReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("找不到商品"));

            OrderDetailId id = new OrderDetailId(order.getOrderId(), product.getProductId());

            // 建立單筆訂單明細
            OrderDetail detail = OrderDetail.builder()
                    .id(id)
                    .order(order) // 關聯訂單
                    .product(product) // 商品
                    .quantity(detailReq.getQuantity()) // 數量
                    .build();

            orderDetails.add(detail);
        }

        // 4️⃣ 設定明細與計算總金額
        orderDetails.forEach(order::addOrderDetail); // 使用 helper 同步雙向
        // 計算整張訂單總金額 = 每筆商品的單價 × 數量，加總起來
        BigDecimal totalAmount = orderDetails.stream()
                .map(od -> od.getProduct().getPrice().multiply(BigDecimal.valueOf(od.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 設定訂單明細與總金額
        order.setTotalAmount(totalAmount);

        // order.setOrderDetails(orderDetails);

        // 儲存訂單（包含主檔與明細）
        return orderRepository.save(order);
    }

    // 更新訂單
    public Order updateOrder(Long orderId, UpdateOrderRequest request) {
        // 先取得欲更新的訂單
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("找不到訂單"));

        // 更新訂單主要欄位
        order.setCustomer(customerService.getCustomerById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("找不到客戶")));
        order.setEmployee(employeeService.getEmployeeById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("找不到員工")));
        order.setOrderDate(request.getOrderDate());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaymentStatus(request.getPaymentStatus());
        order.setOrderStatus(request.getOrderStatus());

        // 處理訂單明細：保留原本的 OrderDetailId，更新內容
        List<OrderDetail> updatedDetails = new ArrayList<>();
        for (UpdateOrderDetailRequest detailReq : request.getOrderDetails()) {
            OrderDetail detail;

            // 用複合主鍵查詢現有明細
            if (detailReq.getOrderId() != null && detailReq.getProductId() != null) {
                OrderDetailId id = new OrderDetailId(detailReq.getOrderId(), detailReq.getProductId());
                detail = orderDetailRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("找不到訂單明細"));
            } else {
                detail = new OrderDetail();
                detail.setOrder(order);
                // 這裡 productId 會在後面設置
            }

            detail.setProduct(productService.getProductById(detailReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("找不到商品")));
            detail.setQuantity(detailReq.getQuantity());

            updatedDetails.add(detail);
        }

        order.setOrderDetails(updatedDetails);

        // 如果需要，自動計算訂單總金額
        BigDecimal totalAmount = updatedDetails.stream()
                .map(d -> d.getProduct().getPrice().multiply(BigDecimal.valueOf(d.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);

        // 儲存並回傳更新後的訂單
        return orderRepository.save(order);
    }

    // 刪除訂單
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    // 多重查詢訂單
    public List<Order> searchOrders(
            Long customerId,
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate,
            Order.OrderStatus orderStatus,
            Order.PaymentStatus paymentStatus) {

        Specification<Order> spec = null;

        if (customerId != null) {
            spec = (spec == null) ? OrderSpecifications.hasCustomerId(customerId) : spec.and(OrderSpecifications.hasCustomerId(customerId));
        }

        if (employeeId != null) {
            spec = (spec == null) ? OrderSpecifications.hasEmployeeId(employeeId) : spec.and(OrderSpecifications.hasEmployeeId(employeeId));
        }

        if (startDate != null || endDate != null) {
            Specification<Order> dateSpec = OrderSpecifications.orderDateBetween(startDate, endDate);
            spec = (spec == null) ? dateSpec : spec.and(dateSpec);
        }

        if (orderStatus != null) {
            spec = (spec == null) ? OrderSpecifications.hasOrderStatus(orderStatus) : spec.and(OrderSpecifications.hasOrderStatus(orderStatus));
        }

        if (paymentStatus != null) {
            spec = (spec == null) ? OrderSpecifications.hasPaymentStatus(paymentStatus) : spec.and(OrderSpecifications.hasPaymentStatus(paymentStatus));
        }

        return orderRepository.findAll(spec);
    }
}

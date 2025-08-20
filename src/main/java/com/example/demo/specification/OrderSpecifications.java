package com.example.demo.specification;

import com.example.demo.model.Order;
import com.example.demo.model.Order.OrderStatus;
import com.example.demo.model.Order.PaymentStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class OrderSpecifications {

    public static Specification<Order> hasCustomerId(Long customerId) {
        return (root, query, cb) -> customerId == null ? null : cb.equal(root.get("customer").get("customerId"), customerId);
    }

    public static Specification<Order> hasEmployeeId(Long employeeId) {
        return (root, query, cb) -> employeeId == null ? null : cb.equal(root.get("employee").get("employeeId"), employeeId);
    }

    public static Specification<Order> orderDateBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) return null;
        if (startDate == null) return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("orderDate"), endDate);
        if (endDate == null) return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("orderDate"), startDate);
        return (root, query, cb) -> cb.between(root.get("orderDate"), startDate, endDate);
    }

    public static Specification<Order> hasOrderStatus(OrderStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("orderStatus"), status);
    }

    public static Specification<Order> hasPaymentStatus(PaymentStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("paymentStatus"), status);
    }
}

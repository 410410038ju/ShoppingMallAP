package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // 可以加自定義查詢，例如：
    Customer findByName(String name);
    Customer findByEmail(String email);
    Customer findByPhone(String phone);
    Customer findByEmailOrPhone(String email, String phone);
}

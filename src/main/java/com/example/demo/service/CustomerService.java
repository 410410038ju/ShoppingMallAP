package com.example.demo.service;

import com.example.demo.dto.request.CreateCustomerRequest;
import com.example.demo.dto.request.UpdateCustomerRequest;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    // 注入 CustomerRepository，負責資料庫操作
    private final CustomerRepository customerRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 取得所有客戶資料，回傳 List<Customer>
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // 透過客戶ID查詢單一客戶，回傳 Optional<Customer>，避免null
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // 新增或更新客戶資料，回傳儲存後的 Customer 物件
    public Customer saveCustomer(Customer customer) {

        if (customer.getPassword() != null) {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        }
        return customerRepository.save(customer);
    }

    // 根據客戶ID刪除對應客戶資料，沒有回傳值
//    public void deleteCustomer(Long id) {
//        customerRepository.deleteById(id);
//    }
    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    // 新增：DTO轉Entity並存新客戶
    public Customer createCustomer(CreateCustomerRequest dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setPassword(dto.getPassword());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
        return saveCustomer(customer);
    }

    // 新增：用DTO更新指定id的客戶
    public Customer updateCustomer(Long id, UpdateCustomerRequest dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("找不到客戶"));
        customer.setName(dto.getName());
        customer.setPassword(dto.getPassword());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
        return customerRepository.save(customer);
    }

    // 根據 Email 或手機號碼查詢
    public Optional<Customer> findByEmailOrPhone(String identifier) {
        return Optional.ofNullable(customerRepository.findByEmailOrPhone(identifier, identifier));
    }

}

package com.example.demo.controller;

import com.example.demo.dto.request.CreateCustomerRequest;
import com.example.demo.model.Customer;
import com.example.demo.dto.request.UpdateCustomerRequest;
import com.example.demo.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "客戶管理")
public class CustomerController {

    private final CustomerService customerService;

    // GET /api/customers
    // 取得所有客戶資料，回傳 List<Customer>
    @Operation(summary = "取得所有客戶資料")
    @GetMapping("/common/customers")
    public List<Customer> getAll() {
        return customerService.getAllCustomers();
    }

    // GET /api/customers/{id}
    // 根據路徑參數 id 取得單一客戶資料，回傳 Optional<Customer>
    @Operation(summary = "根據ID取得客戶資料")
    @GetMapping("/common/customers/{customerId}")
    public Optional<Customer> getById(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    // POST /api/customers
    // 新增一筆客戶資料，接收 JSON 格式的 Customer 物件，並回傳儲存結果
    @Operation(summary = "新增客戶")
    @PostMapping("/customer/customers")
    public Customer create(@RequestBody CreateCustomerRequest dto) {

        return customerService.createCustomer(dto);
    }

    // PUT /api/customers/{id}
    // 修改指定 id 的客戶資料，先設定 id 再呼叫儲存
    @Operation(summary = "更新客戶")
    @PutMapping("/customer/customers/{customerId}")
    public Customer update(@PathVariable Long customerId, @RequestBody UpdateCustomerRequest dto) {

        return customerService.updateCustomer(customerId, dto);
    }

    // DELETE /api/customers/{id}
    // 刪除指定 id 的客戶資料
    @Operation(summary = "刪除客戶")
    @DeleteMapping("/customer/customers/{customerId}")
    public void delete(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
    }
}

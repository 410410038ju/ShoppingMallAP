package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/employee/employees")
@RequiredArgsConstructor
@Tag(name = "員工管理")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "取得所有員工資料")
    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAllEmployees();
    }

    @Operation(summary = "根據ID取得員工資料")
    @GetMapping("/{employeeId}")
    public Optional<Employee> getById(@PathVariable Long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @Operation(summary = "新增員工")
    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @Operation(summary = "更新員工")
    @PutMapping("/{employeeId}")
    public Employee update(@PathVariable Long employeeId, @RequestBody Employee employee) {
        employee.setEmployeeId(employeeId);
        return employeeService.saveEmployee(employee);
    }

    @Operation(summary = "刪除員工")
    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}

package com.example.demo.controller;

import com.example.demo.dto.request.CreateEmployeeRequest;
import com.example.demo.dto.request.UpdateEmployeeRequest;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import com.example.demo.utils.SvcResModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SvcResModel<List<Employee>>> getAll() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(SvcResModel.success(employees));
    }

    @Operation(summary = "根據ID取得員工資料")
    @GetMapping("/{employeeId}")
    public ResponseEntity<SvcResModel<Employee>> getById(@PathVariable Long employeeId) {
        Optional<Employee> employeeOpt = employeeService.getEmployeeById(employeeId);
        return employeeOpt
                .map(employee -> ResponseEntity.ok(SvcResModel.success(employee)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(SvcResModel.<Employee>error("找不到指定的員工")));
    }

    @Operation(summary = "新增員工")
    @PostMapping
    public ResponseEntity<SvcResModel<Employee>> create(
            @RequestBody @Valid CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setEmployeeId(request.getEmployeeId());
        employee.setName(request.getName());
        employee.setPassword(request.getPassword());

        Employee savedEmployee = employeeService.saveEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SvcResModel.success(savedEmployee));
    }

    @Operation(summary = "更新員工")
    @PutMapping("/{employeeId}")
    /*public ResponseEntity<SvcResModel<Employee>> update(@PathVariable Long employeeId,
                                                        @RequestBody Employee employee) {
        employee.setEmployeeId(employeeId);
        Employee updatedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(SvcResModel.success(updatedEmployee));
    }*/
    public ResponseEntity<SvcResModel<Employee>> update(
            @PathVariable Long employeeId,
            @RequestBody @Valid UpdateEmployeeRequest request) {

        Employee updatedEmployee = employeeService.updateEmployee(employeeId, request);

        return ResponseEntity.ok(SvcResModel.success(updatedEmployee));
    }

    @Operation(summary = "刪除員工")
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<SvcResModel<Object>> delete(@PathVariable Long employeeId) {
        boolean deleted = employeeService.deleteEmployee(employeeId);
        if (deleted) {
            return ResponseEntity.ok(SvcResModel.success("刪除成功", null));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(SvcResModel.<Object>error("找不到指定的員工，無法刪除"));
        }
    }
}

package com.example.demo.service;

import com.example.demo.dto.request.CreateEmployeeRequest;
import com.example.demo.dto.request.UpdateEmployeeRequest;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    /*public Employee saveEmployee(Employee employee) {

        if (employee.getPassword() != null) {
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        }
        return employeeRepository.save(employee);
    }*/
    public Employee saveEmployee(CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setEmployeeId(request.getEmployeeId());
        employee.setName(request.getName());

        // 如果是新建員工或修改密碼，才加密
        if (request.getPassword() != null) {
            employee.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return employeeRepository.save(employee);
    }
    
    public Employee updateEmployee(Long employeeId, UpdateEmployeeRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("找不到指定員工"));

        // 更新姓名
        employee.setName(request.getName());

        // 更新密碼（非空才加密）
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            employee.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return employeeRepository.save(employee);
    }


    public boolean deleteEmployee(Long employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            return true;
        } else {
            return false;
        }
    }
}

package com.example.demo.controller;

import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.model.Customer;
import com.example.demo.model.Employee;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.CustomerService;
import com.example.demo.service.EmployeeService;
import com.example.demo.utils.SvcResModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "使用者相關")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Operation(summary = "登入")
    @PostMapping("/login")
    public ResponseEntity<SvcResModel<LoginResponseDTO>> login(@RequestBody LoginRequestDTO loginRequest) {
        String identifier = loginRequest.getIdentifier();
        String password = loginRequest.getPassword();

        // 嘗試使用員工編號登入
        if (identifier.matches("\\d+")) {
            Optional<Employee> employeeOpt = employeeService.getEmployeeById(Long.parseLong(identifier));
            if (employeeOpt.isPresent()) {
                Employee employee = employeeOpt.get();
                if (passwordEncoder.matches(password, employee.getPassword())) {
                    String token = jwtUtil.generateToken(employee.getEmployeeId().toString(), "ROLE_EMPLOYEE");
                    LoginResponseDTO responseDTO = new LoginResponseDTO(token, "ROLE_EMPLOYEE");
                    return ResponseEntity.ok(SvcResModel.success(responseDTO));
                }
            }
        }

        // 嘗試使用 Email 或 Phone 登入（客戶）
        Optional<Customer> customerOpt = customerService.findByEmailOrPhone(identifier);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (passwordEncoder.matches(password, customer.getPassword())) {
                String token = jwtUtil.generateToken(customer.getCustomerId().toString(), "ROLE_CUSTOMER");
                LoginResponseDTO responseDTO = new LoginResponseDTO(token, "ROLE_CUSTOMER");
                return ResponseEntity.ok(SvcResModel.success(responseDTO));
            }
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(SvcResModel.<LoginResponseDTO>error("帳號或密碼錯誤"));
    }
}




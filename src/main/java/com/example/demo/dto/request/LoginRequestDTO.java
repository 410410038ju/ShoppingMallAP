package com.example.demo.dto.request;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String identifier; // 員工是 employeeId，客戶是 email 或 phone
    private String password;
}

package com.example.demo.dto.request;

import lombok.Data;

@Data
public class UpdateCustomerRequest {
    private String name;
    private String password;
    private String email;
    private String phone;
    private String address;
}


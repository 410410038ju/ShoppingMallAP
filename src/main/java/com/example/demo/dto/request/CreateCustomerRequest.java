package com.example.demo.dto.request;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class CreateCustomerRequest {

    @NotBlank(message = "姓名不能為空")
    private String name;

    @NotBlank(message = "電話不能為空")
    private String phone;

    @Email(message = "Email 格式錯誤")
    private String email;

    @NotBlank(message = "密碼不能為空")
    private String password;

    @NotBlank(message = "地址不能為空")
    private String address;

}

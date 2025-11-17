package com.example.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateEmployeeRequest {

    @NotNull
    private Long employeeId;

    @NotBlank(message = "姓名不能為空")
    private String name;

    @NotBlank(message = "密碼不能為空")
    private String password;

}

package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateEmployeeRequest {

    @NotBlank(message = "姓名不能為空")
    private String name;

    private String password; // 可選，如果不填則不更新密碼

}

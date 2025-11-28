package com.example.demo.msg;

import com.example.demo.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategoryMsg {
    NOT_FOUND(ErrorCode.PC001, "查無商品類別"),
    REPEAT_NAME(ErrorCode.PC002, "該商品類別已存在，請勿重複命名");

    private final String code;
    private final String msg;
}

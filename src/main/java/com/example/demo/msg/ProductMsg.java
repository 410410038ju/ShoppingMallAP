package com.example.demo.msg;

import com.example.demo.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductMsg {
    NOT_FOUND(ErrorCode.PR001, "查無商品");

    private final String code;
    private final String msg;
}
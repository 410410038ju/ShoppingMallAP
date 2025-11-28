package com.example.demo.msg;

import com.example.demo.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomerMsg {
    NOT_FOUND(ErrorCode.CU001, "查無客戶");

    private final String code;
    private final String msg;
}




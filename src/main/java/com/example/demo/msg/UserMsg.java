package com.example.demo.msg;

import com.example.demo.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserMsg {
    NOT_FOUND(ErrorCode.UE001, "使用者不存在");

    private final String code;
    private final String msg;
}

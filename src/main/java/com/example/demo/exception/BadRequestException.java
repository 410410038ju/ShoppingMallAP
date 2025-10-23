package com.example.demo.exception;

import lombok.Getter;

// 400
@Getter
public class BadRequestException extends RuntimeException  {

    private String code;
    private String message;

    /**
     * @param message 自定義返回的錯誤訊息
     */
    public BadRequestException(String message) {
        this.message = message;
    }

    public BadRequestException(String code, String message) {
        this.code = code;
        this.message = message;
    }

}

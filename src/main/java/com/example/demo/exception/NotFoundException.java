package com.example.demo.exception;

import lombok.Getter;

// 404
@Getter
public class NotFoundException extends RuntimeException  {

    private String code;
    private String message;

    /**
     * @param message 自定義返回的錯誤訊息
     */
    public NotFoundException(String message) {
        this.message = message;
    }

    public NotFoundException(String code, String message) {
        this.code = code;
        this.message = message;
    }

}

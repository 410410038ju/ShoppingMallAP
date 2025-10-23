package com.example.demo.exception;

import lombok.Getter;

// 500
@Getter
public class SvcErrorException extends RuntimeException {

    private String code;
    private String message;

    /**
     * @param message 自定義返回的錯誤訊息
     */
    public SvcErrorException(String message) {
        this.message = message;
    }

    public SvcErrorException(String code, String message) {
        this.code = code;
        this.message = message;
    }

}

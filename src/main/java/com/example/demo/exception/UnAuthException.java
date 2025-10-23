package com.example.demo.exception;

import lombok.Getter;

// 401
@Getter
public class UnAuthException extends RuntimeException  {

    private String code;
    private String message;

    /**
     * @param message 自定義返回的錯誤訊息
     */
    public UnAuthException(String message) {
        this.message = message;
    }

    public UnAuthException(String code, String message) {
        this.code = code;
        this.message = message;
    }

}



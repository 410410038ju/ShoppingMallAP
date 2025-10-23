package com.example.demo.exception;

import lombok.Getter;

// 403
@Getter
public class NoPowerException extends RuntimeException  {

    private String code;
    private String message;

    /**
     * @param message 自定義返回的錯誤訊息
     */
    public NoPowerException(String message) {
        this.message = message;
    }

    public NoPowerException(String code, String message) {
        this.code = code;
        this.message = message;
    }

}


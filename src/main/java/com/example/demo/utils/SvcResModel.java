package com.example.demo.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Schema(description = "response 下行模板")
public class SvcResModel<T> {
    private static final String SUCCESS_CODE = "0000";
    private static final String SUCCESS_MSG = "成功";
    private static final String ERROR_CODE = "9999";
    private static final String ERROR_MSG = "系統錯誤";

    @Schema(description = "代碼", example = "0000")
    private String code;

    @Schema(description = "訊息", example = "成功")
    private String message;

    @Schema(description = "回傳的資料")
    private T data;

    @Schema(description = "補充資訊(僅開發人員可見)")
    private Map<String, Object> details = new HashMap<>();

    private SvcResModel() {

    }

    private SvcResModel(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private SvcResModel(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> SvcResModel<T> success() {
        return new SvcResModel<>(SUCCESS_CODE, SUCCESS_MSG);
    }

    public static <T> SvcResModel<T> success(String message, T data) {
        return new SvcResModel<>(SUCCESS_CODE, message, data);
    }

    public static <T> SvcResModel<T> success(T data) {
        return new SvcResModel<>(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public static <T> SvcResModel<T> error() {
        return new SvcResModel<>(ERROR_CODE, ERROR_MSG);
    }

    public static <T> SvcResModel<T> error(String message) {
        return new SvcResModel<>(ERROR_CODE, message);
    }

    public static <T> SvcResModel<T> error(String code, String message) {
        return new SvcResModel<>(code, message);
    }

    public SvcResModel<T> put(String key, Object value) {
        this.details.put(key, value);
        return this;
    }
}

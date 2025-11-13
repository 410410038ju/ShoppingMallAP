package com.example.demo.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    ACTIVE("active", "上架中"),
    INACTIVE("inactive", "下架中");

    @JsonValue
    private final String status;
    private final String desc;

    @JsonCreator
    public static ProductStatus getEnum(String status) {
        for (ProductStatus productStatus : ProductStatus.values()) {
            if (productStatus.getStatus().equals(status)) {
                return productStatus;
            }
        }
        throw new IllegalArgumentException("無符合的Enum，ProductStatus: " + status);
    }
}

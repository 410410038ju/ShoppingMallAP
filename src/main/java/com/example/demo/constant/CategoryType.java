package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryType {

    UNCATEGORIZED("未分類"),
    FEATURED("精選商品"),
    NEW_ARRIVALS("新品上市"),
    EXCLUSIVE("獨家商品"),
    REGULAR("一般商品");

    private final String desc;
}

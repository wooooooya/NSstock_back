package com.example.stocks.entity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 금 무게 단위 타입
@Getter
@AllArgsConstructor
public enum GoldType {
    GOLD_1KG("금 1kg"),
    MINI_GOLD_100G("미니금 100g");

    private final String label;
}

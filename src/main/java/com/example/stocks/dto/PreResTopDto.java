package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// 거래량 Top10 (초기 Prediction 페이지 렌더링 시 및 검색값이 null 일 때 반환)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreResTopDto {
    private String shortcode;        // short_code
    private String stocks;      // 종목명
    private BigDecimal price;   // 주식 가격
}

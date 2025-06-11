package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 종목 조회 리스트
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreResStockListDto {
    private String stockID; // Short_code
    private String stockName; // 종목명
    private int stockPrice; // 종목 가격
    private long volume; // 거래량
}

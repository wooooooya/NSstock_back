package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// 거래량 (상위 Top 10)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaResTradingVolumeDto {
    private String stocks; // 종목명
    private long volume; // 거래량
    private long volumeIndecrease; // 전일 대비 거래량 증감
    private BigDecimal volumePercentage; // 거래량 증감률 (%)
}

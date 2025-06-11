package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// 지정 날짜의 차트 데이터
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreResStockChartDto {
    private String shortCode; // short_code
    private ChartSummary weekChart;   // 최근 7개 거래 (주)
    private ChartSummary monthChart;  // 최근 30개 거래 (월)
    private ChartSummary yearChart;   // 최근 월 12개 거래 (각각 월 평균값으로 구성)

    // 기간 날짜
    @Getter
    @Builder
    public static class ChartSummary {
        private long openingPrice;   // 기간 내 시작가
        private long closingPrice;   // 기간 내 마지막 종가
        private long highestPrice;   // 기간 내 최고가
        private long lowestPrice;    // 기간 내 최저가
        private List<StockChart> stockCharts;  // 해당 기간의 개별 차트 데이터 리스트
    }

    // 단일 날짜
    @Getter
    @Builder
    public static class StockChart {
        private long openingPrice;   // 해당 날짜의 시작가
        private long closingPrice;   // 해당 날짜의 종가
        private long highestPrice;   // 해당 날짜의 최고가
        private long lowestPrice;    // 해당 날짜의 최저가
        private String date;         // 해당 날짜
    }
}

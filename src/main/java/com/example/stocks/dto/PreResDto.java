package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// Prediction Response
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PreResDto {
    private String code; // 성공 코드
    private String message; // 성공 메시지
    private List<PreResTopDto> topStockList; // 초기 종목 목록 (거래량 Top10)
    private PreResStockChartDto stockChart; // 선택된 종목 차트 데이터
    private PreResRateDto preRate; // 예측 데이터 리스트
}

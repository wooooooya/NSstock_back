package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

// 금 (사용 X)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaResGoldPriceDto {
    private MaResDto.PreviousClose goldPreviousClose; // 전일 유가 정보
    private List<GoldChart> goldChat; // 유가 차트 데이터

    // 금 차트
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GoldChart {
        private long gChat; // 시점의 유가 값
        private String gDate; // 해당 시점
    }
}

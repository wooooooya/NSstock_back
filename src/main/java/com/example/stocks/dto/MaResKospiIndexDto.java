package com.example.stocks.dto;

import lombok.*;

import java.util.List;

// 코스피
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public  class MaResKospiIndexDto {
    private MaResDto.PreviousClose kospiPreviousClose; // 전일 종가 정보
    private List<KospiChart> kospiChat; // 코스피 지수 차트 데이터

    //코스피 차트
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class KospiChart {
        private long kChat; // 시점의 코스피 지수
        private String kDate; // 해당 날짜
    }
}
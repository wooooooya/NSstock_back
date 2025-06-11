package com.example.stocks.dto;

import lombok.*;

import java.util.List;

// 유가
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaResOilPriceDto {
    private List<OilTypeData> oilTypes; // 유종별 데이터 리스트 (휘발유, 경유, 등유)

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OilTypeData {
        private String oilType; // 휘발유, 경유, 등유
        private MaResDto.PreviousClose oilPreviousClose;
        private List<OilChart> oilChat;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OilChart {
        private long oChat;
        private String oDate;
    }
}
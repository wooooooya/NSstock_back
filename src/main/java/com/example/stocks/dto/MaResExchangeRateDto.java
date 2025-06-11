package com.example.stocks.dto;

import lombok.*;

import java.util.List;

// 환율
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public  class MaResExchangeRateDto {
    private MaResDto.PreviousClose exchangePreviousClose;
    private List<ExchangeChart> exchangeChat;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ExchangeChart {
        private long eChat;
        private String eDate;
    }
}

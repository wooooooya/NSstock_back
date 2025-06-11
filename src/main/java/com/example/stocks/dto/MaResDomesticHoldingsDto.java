package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

// 개인 보유량
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaResDomesticHoldingsDto {
    private MaResDto.HoldingsPrevious dPreviousHoldings; // 전일 대비 개인 보유량
    private List<MaResDto.HoldingsChart> dChat; // 개인 보유량 차트
}

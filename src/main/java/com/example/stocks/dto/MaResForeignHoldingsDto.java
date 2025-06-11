package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

// 외국인 보유량 (사용 X)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaResForeignHoldingsDto {
    private MaResDto.HoldingsPrevious fPreviousHoldings; // 전일 대비 외국인 보유량
    private List<MaResDto.HoldingsChart> fChat; // 외국인 보유량 차트
}

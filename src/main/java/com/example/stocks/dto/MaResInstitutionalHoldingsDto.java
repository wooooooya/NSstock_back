package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

// 기관 보유량 정보 (사용 X)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaResInstitutionalHoldingsDto {
    private MaResDto.HoldingsPrevious iPreviousHoldings; // 전일 대비 기관 보유량
    private List<MaResDto.HoldingsChart> iChat; // 기관 보유량 차트
}

package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// 예측값 데이터 인터페이스 리스트로 묶음
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreResRateDto {
    private List<PreResPredictedDto> previousList;
}


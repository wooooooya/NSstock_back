package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Prediction Request
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreReqDto {
    private String shortCode;  // 주식 코드 (예: 삼성전자)
}

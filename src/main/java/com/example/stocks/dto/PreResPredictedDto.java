package com.example.stocks.dto;

import java.math.BigDecimal;
import java.util.Date;

// 예측값 리스트 인터페이스
public interface PreResPredictedDto {
    String getShortCode();
    BigDecimal getPreviousPrice();
    BigDecimal getPreviousPercentage();
    Date getPreviousDate();
}
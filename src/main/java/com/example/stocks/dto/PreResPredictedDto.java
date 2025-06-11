package com.example.stocks.dto;

import java.util.Date;

// 예측값 리스트 인터페이스
public interface PreResPredictedDto {
    String getShortCode();
    Long getPreviousPrice();
    Double getPreviousPercentage();
    Date getPreviousDate();
}
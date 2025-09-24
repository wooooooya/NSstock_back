package com.example.stocks.dto;

import java.math.BigDecimal;
import java.util.Date;

public interface PreResStockListDto {
    String getStockID();
    String getStockName();
    Long getStockPrice();
    Long getVolume();
    Date getDate();
    Long getPriceChange(); // price_change 값을 받을 메서드
    BigDecimal getPriceChangeRate(); // price_change_rate 값을 받을 메서드
}

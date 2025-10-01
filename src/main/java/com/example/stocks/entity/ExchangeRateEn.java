//package com.example.stocks.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//// exchange_rate 테이블
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity(name="exchange_rate")
//@Table(name="exchange_rate")
//public class ExchangeRateEn {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int exchangeId;
//
//    @Column(nullable = false, length = 10)
//    private String currencyCode;
//
//    @Column(nullable = false)
//    private LocalDate date;
//
//    @Column(nullable = false, precision = 10, scale = 4)
//    private BigDecimal exchangeRate;
//
//    @Column(nullable = false, precision = 10, scale = 2)
//    private BigDecimal change;
//
//    @Column(nullable = false, precision = 10, scale = 2)
//    private BigDecimal openingPrice;
//
//    @Column(nullable = false, precision = 10, scale = 2)
//    private BigDecimal highestPrice;
//
//    @Column(nullable = false, precision = 10, scale = 2)
//    private BigDecimal lowestPrice;
//
//    @Column(nullable = false, precision = 10, scale = 2)
//    private BigDecimal closingPrice;
//
//    @Column(nullable = false, precision = 10, scale = 2)
//    private BigDecimal tradingVolume;
//}

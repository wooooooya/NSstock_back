package com.example.stocks.entity;

import com.example.stocks.entity.enumeration.GoldType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

// gold_price 테이블
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="gold_price")
@Table(name="gold_price")
public class GoldPriceEn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int goldId;

    @Column(nullable = false)
    private String goldCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoldType goldType;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int closingPrice;

    @Column(nullable = false)
    private int goldChange;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal goldChangeRate;

    @Column(nullable = false)
    private int openingPrice;

    @Column(nullable = false)
    private int highestPrice;

    @Column(nullable = false)
    private int lowestPrice;

    @Column(nullable = false)
    private int tradingVolume;

    @Column(nullable = false)
    private Long tradingValue;
}

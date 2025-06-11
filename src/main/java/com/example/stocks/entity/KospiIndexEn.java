package com.example.stocks.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

// kospi_index 테이블
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "kospi_index")
@Table(name = "kospi_index")
public class KospiIndexEn {

    @Id
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal closingPrice;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal kospiChange;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal kospiChangeRate;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal openingPrice;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal highestPrice;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal lowestPrice;

    @Column(nullable = false)
    private int tradingVolume;

    @Column(nullable = false)
    private Long tradingValue;

    @Column(nullable = false)
    private Long marketCap;
}

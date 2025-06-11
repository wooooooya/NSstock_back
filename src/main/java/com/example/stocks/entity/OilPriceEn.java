package com.example.stocks.entity;

import com.example.stocks.entity.enumeration.OilType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

// oli_price 테이블
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="oil_price")
@Table(name="oil_price")
public class OilPriceEn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oilId;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OilType oilType;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal averagePriceCompetition;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal averagePriceConsultation;

    @Column(nullable = false, length = 20)
    private Long tradingVolume;

    @Column(nullable = false, length = 20)
    private Long tradingValue;
}

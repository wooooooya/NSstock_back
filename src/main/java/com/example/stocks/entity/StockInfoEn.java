package com.example.stocks.entity;

import com.example.stocks.entity.enumeration.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

// stock_info 테이블
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name="stock_info")
@Table(name="stock_info")
public class StockInfoEn {

    @Id
    @Column(length = 15)
    private String shortCode;

    @Column(length = 30,nullable = false)
    private String standardCode;

    @Column(length = 50,nullable = false)
    private String korStockName;

    @Column(length = 20,nullable = false)
    private String korShortStockName;

    @Column(length = 100,nullable = false)
    private String engStockName;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date listingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MarketType marketType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CertificateType certificateType;

    @Column(length = 100)
    private String department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockType stockType;

    @Column(nullable = false)
    private Integer faceValue; // int -> Integer 로 변경

    @Column(nullable = false)
    private Long listedStockNum;

    @OneToMany(mappedBy = "stockInfo", fetch = FetchType.LAZY)
    private List<StockPriceEn> stockPrices;
}
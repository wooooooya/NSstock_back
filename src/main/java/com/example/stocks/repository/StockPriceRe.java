package com.example.stocks.repository;

import com.example.stocks.dto.PreResStockListDto;
import com.example.stocks.entity.StockPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

// 주식 가격 리포지토리
@Repository
public interface StockPriceRe extends JpaRepository<StockPriceEn, Long> {

    // 가장 최근 날짜 기준으로 거래량이 많은 상위 10개 종목
    @Query(value = """
        SELECT
            sp.short_code AS stockID,
            si.kor_stock_name AS stockName,
            sp.closing_price AS stockPrice, 
            sp.trading_volume AS volume,
            sp.date AS date,
            sp.price_change AS priceChange,
            sp.price_change_rate AS priceChangeRate
        FROM stock_price sp
        JOIN stock_info si ON sp.short_code = si.short_code
        WHERE sp.date = (SELECT MAX(date) FROM stock_price)
        ORDER BY sp.trading_volume DESC
        LIMIT 10
        """, nativeQuery = true)
    List<PreResStockListDto> findTop10ByRecentDateOrderByTradingVolumeDesc();

    @Query(value = """
        SELECT *
        FROM stock_price
        WHERE short_code = :shortCode AND date < :targetDate
        ORDER BY date DESC
        LIMIT 1
        """, nativeQuery = true)
    Optional<StockPriceEn> findPreviousDayStockPrice(
            @Param("shortCode") String shortCode,
            @Param("targetDate") Date targetDate
    );

    @Query(value = """
        SELECT
            sp.short_code AS stockID,
            si.kor_stock_name AS stockName,
            sp.closing_price AS stockPrice,
            sp.trading_volume AS volume
        FROM stock_price sp
        JOIN stock_info si ON sp.short_code = si.short_code
        WHERE sp.date = (SELECT MAX(date) FROM stock_price)
        AND sp.short_code = :shortCode
        """, nativeQuery = true)
    List<PreResStockListDto> findByRecentDateAndShortCodeDto(@Param("shortCode") String shortCode);

    @Query(value = """
        SELECT
            DATE_FORMAT(date, '%Y-%m') AS month,
            ROUND(AVG(opening_price)) AS opening_price,
            ROUND(AVG(closing_price)) AS closing_price,
            ROUND(AVG(highest_price)) AS highest_price,
            ROUND(AVG(lowest_price)) AS lowest_price
        FROM stock_price
        WHERE short_code = :shortCode
        AND date BETWEEN :startDate AND :endDate
        GROUP BY month
        ORDER BY month ASC
        """, nativeQuery = true)
    List<Object[]> findMonthlyAvgChartData(
            @Param("shortCode") String shortCode,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query(value = """
        SELECT * FROM stock_price
        WHERE short_code = :shortCode
        ORDER BY date DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<StockPriceEn> findRecentStockPrices(
            @Param("shortCode") String shortCode,
            @Param("limit") int limit
    );
}
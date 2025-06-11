package com.example.stocks.repository;

import com.example.stocks.dto.PreResStockListDto;
import com.example.stocks.entity.StockPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

// 주식 가격 리포지토리
@Repository
public interface StockPriceRe extends JpaRepository<StockPriceEn, Long> {

    // 가장 최근 날짜 기준으로 거래량이 많은 상위 10개 종목
    // @return 거래량 기준 상위 10개 종목의 목록
    @Query(value = """
        SELECT 
            sp.short_code AS stockID,                -- 종목 코드
            si.kor_stock_name AS stockName,          -- 종목 이름
            sp.closing_price AS stockPrice,          -- 종가
            sp.trading_volume AS volume              -- 거래량
        FROM stock_price sp
        JOIN stock_info si ON sp.short_code = si.short_code
        WHERE sp.date = (SELECT MAX(date) FROM stock_price) -- 가장 최근 날짜 기준
        ORDER BY sp.trading_volume DESC              -- 거래량 내림차순 정렬
        LIMIT 10                                     -- 상위 10개만 조회
        """, nativeQuery = true)
    List<PreResStockListDto> findTop10ByRecentDateOrderByTradingVolumeDesc();

    // @param shortCode 종목 코드
    //@return 해당 종목의 최근 정보
    @Query(value = """
        SELECT 
            sp.short_code AS stockID,                -- 종목 코드
            si.kor_stock_name AS stockName,          -- 종목 이름
            sp.closing_price AS stockPrice,          -- 종가
            sp.trading_volume AS volume              -- 거래량
        FROM stock_price sp
        JOIN stock_info si ON sp.short_code = si.short_code
        WHERE sp.date = (SELECT MAX(date) FROM stock_price) -- 가장 최근 날짜 기준
        AND sp.short_code = :shortCode              -- 주어진 종목 코드 필터링
        """, nativeQuery = true)
    List<PreResStockListDto> findByRecentDateAndShortCodeDto(@Param("shortCode") String shortCode);

    // 이전 1년 동안의 정보 조회
    // 시가, 종가, 고가, 저가의 평균을 계산 후 월 단위로 그룹화
    // @param shortCode 종목 코드
    // @param startDate 시작 날짜
    //@param endDate 종료 날짜
    // @return [월, 시가, 종가, 고가, 저가] 리스트
    @Query(value = """
        SELECT 
            DATE_FORMAT(date, '%Y-%m') AS month,     -- 'YYYY-MM' 형식
            ROUND(AVG(opening_price)) AS opening_price, -- 시가 평균
            ROUND(AVG(closing_price)) AS closing_price, -- 종가 평균
            ROUND(AVG(highest_price)) AS highest_price, -- 고가 평균
            ROUND(AVG(lowest_price)) AS lowest_price    -- 저가 평균
        FROM stock_price
        WHERE short_code = :shortCode
        AND date BETWEEN :startDate AND :endDate      -- 기간 내 데이터 필터링
        GROUP BY month                                -- 월별 그룹화
        ORDER BY month ASC                        -- 월 기준 오름차순 정렬
        """, nativeQuery = true)
    List<Object[]> findMonthlyAvgChartData(
            @Param("shortCode") String shortCode,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    // 특정 종목에 대해 가장 최근의 주가 데이터를 원하는 개수(7개 30개)만큼 조회
    // @param shortCode 종목 코드
    //@param limit 조회할 데이터 수
    //@return 최근 주가 정보 엔티티 목록
    @Query(value = """
        SELECT * FROM stock_price
        WHERE short_code = :shortCode
        ORDER BY date DESC          -- 날짜 기준 최신순 정렬
        LIMIT :limit                -- 지정된 개수만큼 조회
        """, nativeQuery = true)
    List<StockPriceEn> findRecentStockPrices(
            @Param("shortCode") String shortCode,
            @Param("limit") int limit
    );
}

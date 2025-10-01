package com.example.stocks.repository;

import com.example.stocks.dto.PreResPredictedDto;
import com.example.stocks.entity.PredictedStockPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// 예측값 리포지토리
public interface PredictedStockPriceRe extends JpaRepository<PredictedStockPriceEn, Long> {

    //shortCode에 대해 가장 최근 생성된 예측 데이터 중  7일이후의 예측 정보 조회
    // @param shortCode
    // @return 최근 생성된 예측값 7개 (종가, 증감률, 날짜)
    @Query(value = """
        SELECT 
            p.short_code AS shortCode,                 -- 예측 테이블의 종목 코드
            p.predicted_closing_price AS previousPrice, -- 예측 종가
            ROUND(
                ((p.predicted_closing_price - s.closing_price) / s.closing_price) * 100,
                2
            ) AS previousPercentage,                  -- 예측 종가 대비 실제 종가의 상승률(%) (소수점 2자리 반올림)
            p.prediction_date AS previousDate         -- 예측 날짜
        FROM predicted_stock_price p
        JOIN (
            -- 최신 실제 종가 1개 조회 (날짜의 기준이 되는 종가)
            SELECT closing_price
            FROM stock_price
            WHERE short_code = :shortCode
            ORDER BY date DESC
            LIMIT 1
        ) s ON 1=1
        WHERE p.short_code = :shortCode
          AND p.created_at = (
              -- 해당 종목의 가장 최근 예측 데이터 생성 시간 기준으로 필터링
              SELECT MAX(created_at)
              FROM predicted_stock_price
              WHERE short_code = :shortCode
          )
        ORDER BY p.prediction_date ASC       -- 예측 날짜 기준 오름차순 정렬
        LIMIT 7                              -- 최대 7개 데이터 반환 (7일치 예측)
        """, nativeQuery = true)
    List<PreResPredictedDto> findLatestSevenPredictionsByShortCode(@Param("shortCode") String shortCode);
}

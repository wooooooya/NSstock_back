package com.example.stocks.repository;

import com.example.stocks.entity.ExchangeRateEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// 환율 리포지토리
@Repository
public interface ExchangeRateRe extends JpaRepository<ExchangeRateEn, Integer> {

    // 최근 15일 환율값 조회
    @Query(value = """
    SELECT * FROM (
        SELECT * FROM exchange_rate
        WHERE currency_code = :currencyCode
        ORDER BY date DESC
        LIMIT 15
    ) AS recent
    ORDER BY date ASC
    """, nativeQuery = true)
    List<ExchangeRateEn> findLatest15DaysByCurrency(@Param("currencyCode") String currencyCode);
}
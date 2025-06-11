package com.example.stocks.repository;

import com.example.stocks.entity.OilPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// 유가 리포지토리
@Repository
public interface OilPriceRe extends JpaRepository<OilPriceEn, Integer> {

    // 파라미터(휘발유, 경유, 등유) 에 따른 최근 15일 유가 조회
    @Query(value = """
    SELECT * FROM oil_price
    WHERE oil_type = :oilType
        AND date BETWEEN DATE_SUB(
            (SELECT MAX(date) FROM oil_price WHERE oil_type = :oilType), INTERVAL 15 DAY)
        AND (SELECT MAX(date) FROM oil_price WHERE oil_type = :oilType)
    ORDER BY date ASC
    """, nativeQuery = true)
    List<OilPriceEn> findLatest15DaysByOilType(@Param("oilType") String oilType);
}

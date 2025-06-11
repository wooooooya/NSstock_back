package com.example.stocks.repository;

import com.example.stocks.entity.KospiIndexEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// 코스피 리포지토리
@Repository
public interface KospiIndexRe extends JpaRepository<KospiIndexEn, LocalDate> {

    // 최신 날짜 기준 15일 전부터 최신 날짜까지 조회
    @Query(value = """
        SELECT * FROM kospi_index 
        WHERE date BETWEEN DATE_SUB((SELECT MAX(date) FROM kospi_index), INTERVAL 15 DAY) 
                      AND (SELECT MAX(date) FROM kospi_index) 
        ORDER BY date ASC
    """, nativeQuery = true)
    List<KospiIndexEn> findLatest15Days();
}

package com.example.stocks.repository;

import com.example.stocks.entity.StockInfoEn;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// 주식 정보 리포지토리
@Repository
public interface StockInfoRe extends JpaRepository<StockInfoEn, String> {
//    @Query(value = """
//    SELECT * FROM stock_info
//    WHERE short_code LIKE CONCAT('%', :searchTerm, '%')
//       OR kor_stock_name LIKE CONCAT('%', :searchTerm, '%')
//    LIMIT 20
//    """, nativeQuery = true)
//    List<StockInfoEn> findByShortCodeOrKorStockName(@Param("searchTerm") String searchTerm);
}
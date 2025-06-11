package com.example.stocks.repository;

import com.example.stocks.entity.StockInfoEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 주식 정보 리포지토리
@Repository
public interface StockInfoRe extends JpaRepository<StockInfoEn, String> {
}
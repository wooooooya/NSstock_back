package com.example.stocks.repository;

import com.example.stocks.entity.GoldPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 금 리포지토리
@Repository
public interface GoldPriceRe extends JpaRepository<GoldPriceEn, Integer> {
}

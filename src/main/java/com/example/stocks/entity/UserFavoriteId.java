package com.example.stocks.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode // 복합 키 비교를 위한 equals, hashCode 자동 생성
public class UserFavoriteId implements Serializable {
    private String userInfo; // UserFavorite 엔티티의 userInfo 필드명과 일치해야 함
    private String stockCode; // UserFavorite 엔티티의 stockCode 필드명과 일치해야 함
}

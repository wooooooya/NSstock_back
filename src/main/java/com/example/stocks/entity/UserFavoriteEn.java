package com.example.stocks.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_favorites")
@Getter
@Setter
@NoArgsConstructor
@IdClass(UserFavoriteId.class) // 복합 키 클래스를 지정
public class UserFavoriteEn {

    @Id // 복합 키의 일부
    @ManyToOne(fetch = FetchType.LAZY) // 다대일(Many-to-One) 관계
    @JoinColumn(name = "uuid")
    private UserInfoEn userInfo;

    @Id // 복합 키의 일부
    @Column(name = "stock_code", length = 6)
    private String stockCode;
}

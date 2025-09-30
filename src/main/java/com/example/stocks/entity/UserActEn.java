package com.example.stocks.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "useract")
@Getter
@Setter
@NoArgsConstructor
public class UserActEn{

    @Id // 기본 키
    @Column(name = "uuid") // DB의 uuid 컬럼과 매핑
    private String uuid;

    @Column(name = "widget")
    private Integer widget;

    // UserInfo와의 일대일(One-to-One) 관계
    // @MapsId: UserInfo의 ID를 UserAct의 ID로 함께 사용
    @OneToOne
    @MapsId
    @JoinColumn(name = "uuid")
    private UserInfoEn userInfo;
}

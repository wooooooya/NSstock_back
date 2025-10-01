package com.example.stocks.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@NoArgsConstructor
public class UserInfoEn {

    @Id // 기본 키(Primary Key) 지정
    @Column(name = "uuid", length = 36)
    private String uuid;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "login_id", nullable = false, length = 50)
    private String loginId;

    @Column(name = "pw", nullable = false, length = 255)
    private String pw;

    // UserAct와의 일대일(One-to-One) 관계
    // CascadeType.ALL: UserInfo가 저장/삭제될 때 UserAct도 함께 처리
    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserActEn userAct;

    // UserFavorite와의 일대다(One-to-Many) 관계
    // orphanRemoval = true: 컬렉션에서 제거된 UserFavorite는 DB에서도 삭제
    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFavoriteEn> favorites = new ArrayList<>();
}
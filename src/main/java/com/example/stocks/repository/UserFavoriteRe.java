package com.example.stocks.repository;

import com.example.stocks.entity.UserFavoriteEn;
import com.example.stocks.entity.UserFavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavoriteRe extends JpaRepository<UserFavoriteEn, UserFavoriteId> { // <엔티티, 복합 키 타입>

    // 특정 사용자의 모든 즐겨찾기 목록을 조회하는 커스텀 메서드
    // 메서드 이름 규칙: findBy + {연관된 엔티티 필드명} + {그 엔티티의 필드명}
    List<UserFavoriteEn> findByUserInfoUuid(String uuid);
}

package com.example.stocks.repository;

import com.example.stocks.entity.UserInfoEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRe extends JpaRepository<UserInfoEn, String> {

    // 메서드 이름을 'findByLoginId'로 변경
    Optional<UserInfoEn> findByLoginId(String loginId);

    // findByEmail 메서드는 변경 없음
    Optional<UserInfoEn> findByEmail(String email);
}
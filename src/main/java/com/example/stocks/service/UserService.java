package com.example.stocks.service;

import com.example.stocks.dto.*;
import com.example.stocks.entity.*;
import com.example.stocks.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserInfoRe userInfoRepository;
    private final UserFavoriteRe userFavoriteRepository;

    // 1. 회원가입
    @Transactional
    public void signUp(UserSignUpRequestDto requestDto) {
        if (userInfoRepository.findByLoginId(requestDto.getLoginId()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        UserInfoEn newUser = new UserInfoEn();
        newUser.setUuid(UUID.randomUUID().toString());
        newUser.setLoginId(requestDto.getLoginId());
        newUser.setPw(requestDto.getPassword());
        newUser.setEmail(requestDto.getEmail());

        UserActEn userAct = new UserActEn();
        userAct.setWidget(12345678);
        userAct.setUserInfo(newUser);

        newUser.setUserAct(userAct);

        userInfoRepository.save(newUser);
    }

    // 2. 비밀번호 수정
    @Transactional
    public void updatePassword(String loginId, UserPasswordUpdateRequestDto requestDto) {
        UserInfoEn user = userInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // ❗ 현재 비밀번호 일치 여부를 .equals()로 비교
        if (!user.getPw().equals(requestDto.getCurrentPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // ❗ 새로운 비밀번호를 암호화 없이 그대로 저장
        user.setPw(requestDto.getNewPassword());
    }

    // 3. 회원 탈퇴
    @Transactional
    public void withdraw(UserWithdrawalRequestDto requestDto) {
        UserInfoEn user = userInfoRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // ❗ 비밀번호 일치 여부를 .equals()로 비교
        if (!user.getPw().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        userInfoRepository.delete(user);
    }

    // 4. 즐겨찾기 등록
    @Transactional
    public void addFavorite(String loginId, FavoriteAddRequestDto requestDto) {
        UserInfoEn user = userInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        UserFavoriteEn newFavorite = new UserFavoriteEn();
        newFavorite.setUserInfo(user);
        newFavorite.setStockCode(requestDto.getStockCode());

        userFavoriteRepository.save(newFavorite);
    }

    // 5. 로그인
    public String login(UserLoginRequestDto requestDto) {
        UserInfoEn user = userInfoRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        if (!user.getPw().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return "로그인 성공! (보안 기능 없음)";
    }
}
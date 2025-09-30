package com.example.stocks.service;

import com.example.stocks.dto.*;
import com.example.stocks.entity.*;
import com.example.stocks.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class UserService {

    private final UserInfoRe userInfoRepository;
    private final UserFavoriteRe userFavoriteRepository;
    private final PasswordEncoder passwordEncoder;

    // 1. 회원가입
    @Transactional
    public void signUp(UserSignUpRequestDto requestDto) {
        // ID 중복 확인
        if (userInfoRepository.findByLoginId(requestDto.getLoginId()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        // UUID 생성 및 비밀번호 암호화
        String newUuid = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 사용자 정보 저장
        UserInfoEn newUser = new UserInfoEn();
        newUser.setUuid(newUuid);
        newUser.setLoginId(requestDto.getLoginId());
        newUser.setPw(encodedPassword);
        newUser.setEmail(requestDto.getEmail());

        // 사용자 기본 활동 정보(위젯 등) 저장
        UserActEn userAct = new UserActEn();
        userAct.setUuid(newUuid);
        userAct.setWidget(12345678); // 기본값 설정
        userAct.setUserInfo(newUser);

        newUser.setUserAct(userAct); // 연관관계 설정

        userInfoRepository.save(newUser);
    }

    // 2. 비밀번호 수정
    @Transactional
    public void updatePassword(String loginId, UserPasswordUpdateRequestDto requestDto) {
        UserInfoEn user = userInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 현재 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPw())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 새로운 비밀번호 암호화 및 업데이트
        String newEncodedPassword = passwordEncoder.encode(requestDto.getNewPassword());
        user.setPw(newEncodedPassword);
    }

    // 3. 회원 탈퇴
    @Transactional
    public void withdraw(UserWithdrawalRequestDto requestDto) {
        UserInfoEn user = userInfoRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPw())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 사용자 정보 삭제 (ON DELETE CASCADE 덕분에 연관된 데이터도 모두 삭제됨)
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

        // 복합 키 제약조건에 의해 중복 등록 시 자동으로 예외 발생
        userFavoriteRepository.save(newFavorite);
    }

    // 5. 로그인
    public String login(UserLoginRequestDto requestDto) {
        UserInfoEn user = userInfoRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPw())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 로그인 성공 (실제로는 여기서 JWT 토큰 등을 생성하여 반환)
        return "로그인 성공!";
    }
}
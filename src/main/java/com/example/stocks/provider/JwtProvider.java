package com.example.stocks.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * JWT 토큰을 생성하고 관리하기 위한 유틸리티 클래스입니다.
 */
@Component
public class JwtProvider {

    @Value("${jwt-secret-key}")
    private String secretKey;// jwt 토큰을 서명하거나 검증하기 위한 secretKey

    /**
     * JWT Token 생성
     * @param email 이메일
     * @return jwt token 문자열
     */
    public String create(String email){
        // 토큰 만료 시간(생성 시간 이후 1시간)
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        //jwt 생성
        String jwt = Jwts.builder()
                .subject(email) //토큰의 subject에 이메일 설정
                .issuedAt(new Date()) //토큰 발급 시간
                .expiration(expiredDate) //토큰 만료 시간
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes())) //Secret Key로 서명
                .compact();

        return jwt;
    }

    /**
     * JWT Token 검증
     * @param token jwt token 문자열
     * @return 사용자 이메일
     */
    public String validate(String token){
        Claims claims = null;

        //jwt 토큰에서 Claims 파싱
        try{
            claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes())) //서명 검증에 사용할 Secret Key
                    .build()
                    .parseSignedClaims(token) //서명이 포함된 jwt 토큰 검증 및 파싱
                    .getPayload(); //Claims 객체 반환
        } catch (ExpiredJwtException e) {
            // 토큰 만료 예외 처리
            System.out.println("JWT Token Expired : " + e.getMessage());
            return null; // 만료된 경우 null 반환 (원하는 값으로 변경 가능)
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

        //Claims에서 subject 반환
        return claims.getSubject();
    }
}

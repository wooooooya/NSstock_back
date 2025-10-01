//package com.example.stocks.provider;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
///**
// * JWT 토큰 생성, 서명, 검증을 담당하는 컴포넌트입니다.
// * JWT는 사용자 인증 및 인가에 사용되며, 이 클래스는 토큰을 생성하고,
// * 유효성을 검사하며, 토큰 내부에 저장된 사용자 이메일 정보를 추출하는 기능을 제공합니다.
// * 토큰의 키와 만료 시간은 application.properties 파일에서 주입받아 사용합니다.
// */
//@Slf4j
//@Component
//public class JwtProvider {
//
//    //JWT 토큰 서명에 사용할 키 값
//    private final Key key;
//    //JWT 토큰의 유효 기간(ms)
//    private final long tokenValidityInMilliSeconds;
//
//    /**
//     * JwtProvider의 생성자입니다.
//     * Spring의 @Value 어노테이션을 사용하여 외부 설정 파일에서 JWT 키와 유효 기간을 주입받아 초기화합니다.
//     * @param secretkey JWT 서명에 사용할 Base64로 인코딩된 비밀 키
//     * @param tokenValidityInSeconds JWT 토큰의 유효 기간(s)
//     */
//    public JwtProvider(
//            @Value("${jwt.key}") String secretkey,
//            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
//        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
//        this.tokenValidityInMilliSeconds = tokenValidityInSeconds*1000;
//    }
//
//
//    /**
//     * 사용자 이메일로 JWT 토큰을 생성합니다.
//     * @param email 사용자 이메일
//     * @return 생성된 JWT 토큰
//     */
//    public String create(String email){
//        Date now = new Date();
//        Date expiredDate = new Date(now.getTime() + tokenValidityInMilliSeconds);
//
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(now)
//                .setExpiration(expiredDate)
//                .signWith(key,SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    /**
//     * JWT 토큰에서 서명 및 만료 여부를 검사하고, 유효한 경우 이메일을 반환합니다.
//     * @param jwt 검증할 JWT 토큰
//     * @return 토큰에 저장된 사용자 이메일
//     */
//    public String getEmailFromToken(String jwt){
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(jwt)
//                    .getBody();
//
//            return claims.getSubject();
//        } catch (ExpiredJwtException e) {
//            log.info("만료된 JWT 토큰입니다: {}", e.getMessage());
//            throw e;
//        } catch (MalformedJwtException e) {
//            log.info("유효하지 않거나 잘못된 서명의 JWT 토큰입니다: {}", e.getMessage());
//            throw e;
//        } catch (UnsupportedJwtException e) {
//            log.info("지원되지 않는 형식의 JWT 토큰입니다: {}", e.getMessage());
//            throw e;
//        } catch (IllegalArgumentException e) {
//            log.info("JWT 토큰이 비어 있거나 null입니다: {}", e.getMessage());
//            throw e;
//        }
//    }
//}

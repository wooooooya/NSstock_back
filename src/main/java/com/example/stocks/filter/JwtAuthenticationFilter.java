//package com.example.stocks.filter;
//
//import com.example.stocks.provider.JwtProvider;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter { // OncePerRequestFilter 상속
//
//    private final JwtProvider jwtProvider;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        // 1. 요청 헤더에서 토큰을 추출
//        String token = resolveToken(request);
//
//        // 2. 토큰이 유효한지 검사
//        if (token != null) {
//            try {
//                // JwtProvider를 사용해 토큰에서 이메일(사용자 정보)을 가져옴
//                String email = jwtProvider.getEmailFromToken(token);
//
//                // 3. 인증 정보 생성 및 SecurityContext에 저장
//                // (여기서는 간단히 이메일만 사용. UserDetails를 사용하는 것이 더 정석적인 방법)
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(email, null, null);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            } catch (Exception e) {
//                // 토큰이 유효하지 않을 경우, 아무것도 하지 않고 다음 필터로 넘어감 (인증되지 않은 상태로)
//                // 필요하다면 여기서 로깅을 하거나 특정 예외 처리를 할 수 있음
//            }
//        }
//
//        // 4. 다음 필터로 요청을 전달
//        filterChain.doFilter(request, response);
//    }
//
//    // 요청 헤더에서 "Authorization" 필드의 Bearer 토큰을 추출하는 메서드
//    private String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}
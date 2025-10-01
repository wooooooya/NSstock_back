//package com.example.stocks.config;
//
//import com.example.stocks.filter.JwtAuthenticationFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // CSRF, Form Login, HTTP Basic 인증 비활성화
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable);
//
//        // 세션을 사용하지 않도록 설정 (STATELESS)
//        http
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        // 경로별 인가(Authorization) 설정
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        // 허용할 경로들을 명시
//                        .requestMatchers("/api/user/signup", "/api/user/login").permitAll()
//                        .requestMatchers("/api/main", "/api/prediction/**").permitAll()
//                        // 위 경로 외의 모든 요청은 인증 필요
//                        .anyRequest().authenticated()
//                );
//
//        // 직접 만든 JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
//        http
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}
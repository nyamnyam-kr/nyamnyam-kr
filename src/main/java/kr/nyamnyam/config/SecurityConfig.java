package kr.nyamnyam.config;

import kr.nyamnyam.service.impl.TokenServiceImpl; // TokenServiceImpl로 변경
import kr.nyamnyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenServiceImpl tokenService; // JwtTokenProvider 대신 TokenServiceImpl 주입
    private final UserService userService;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenService, userService); // JwtTokenProvider 대신 TokenServiceImpl 사용

        http
                .cors(corsSpec -> corsWebFilter())
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS).permitAll() // OPTIONS 요청 인증 제외
                        .pathMatchers("/api/*").permitAll() // OPTIONS 요청 인증 제외
                        .anyExchange().permitAll() // 다른 모든 요청도 인증 없이 허용
                )
                .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION); // JWT 필터 등록

        return http.build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000")); // 허용할 Origin 설정
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept")); // 모든 헤더 허용
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        config.setAllowCredentials(true); // 인증 관련 정보 허용

        source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 CORS 정책 적용
        return new CorsWebFilter(source);
    }


}
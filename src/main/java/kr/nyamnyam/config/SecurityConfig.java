package kr.nyamnyam.config;

import kr.nyamnyam.service.impl.JwtTokenProvider;
import kr.nyamnyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // JWT 인증 필터 등록
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider, userService);

        // Security Configuration
        http
                .csrf().disable() // CSRF 비활성화 (API 전용)
                .authorizeExchange()
                .pathMatchers("/api/user/login").permitAll() // 로그인 엔드포인트는 인증 없이 접근 가능
                .anyExchange().authenticated() // 나머지 요청은 인증 필요
                .and()
                .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION); // 올바른 타입 사용

        return http.build();
    }
}

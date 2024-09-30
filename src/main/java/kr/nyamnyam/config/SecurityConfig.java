package kr.nyamnyam.config;

import kr.nyamnyam.service.impl.JwtTokenProvider;
import kr.nyamnyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // JWT 인증 필터 등록
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider, userService);

        // 최신 Security Configuration
        http
                .csrf(csrf -> csrf.disable()) // 새로운 방식으로 CSRF 비활성화
                .authorizeExchange(authorize -> authorize
                        .anyExchange().permitAll() // 모든 요청에 대해 인증 없이 접근 가능
                )
                .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION); // 필터 등록

        return http.build();
    }
}

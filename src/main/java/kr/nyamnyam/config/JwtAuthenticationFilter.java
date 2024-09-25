package kr.nyamnyam.config;

import kr.nyamnyam.service.impl.JwtTokenProvider;
import kr.nyamnyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtTokenProvider jwtTokenProvider; // JwtTokenProvider 주입
    private final UserService userService; // UserService 주입

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = resolveToken(exchange.getRequest());

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsername(token);
            // UserService를 통해 사용자 정보를 가져옴
            return userService.findByUsername(username)
                    .flatMap(user -> {
                        // 사용자 정보를 Exchange의 속성에 추가
                        exchange.getAttributes().put("userDetails", user);
                        return chain.filter(exchange); // 다음 필터로 요청 전달
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        // 사용자 정보가 없는 경우 401 반환
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return Mono.empty();
                    }));
        } else {
            // 토큰이 유효하지 않거나 없는 경우 401 반환
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty();
        }
    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

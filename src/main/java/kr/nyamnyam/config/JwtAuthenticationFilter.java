package kr.nyamnyam.config;

import kr.nyamnyam.service.impl.TokenServiceImpl;
import kr.nyamnyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
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

    private final TokenServiceImpl tokenService;
    private final UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("Request Method: " + exchange.getRequest().getMethod());
        System.out.println("Request Path: " + exchange.getRequest().getPath().value());

        if (exchange.getRequest().getMethod().equals(HttpMethod.OPTIONS)) {
            return chain.filter(exchange);
        }

        String requestPath = exchange.getRequest().getPath().value();

        return ("/api/user/register".equals(requestPath) ||
                "/api/user/login".equals(requestPath) ||
                "/api/thumbnails/upload".equals(requestPath))
                ? chain.filter(exchange)
                : Mono.justOrEmpty(resolveToken(exchange.getRequest()))
                .flatMap(tokenService::validateToken)
                .flatMap(isValid -> {
                    if (!isValid) {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return Mono.empty();
                    }

                    String username = tokenService.getUsernameFromToken(resolveToken(exchange.getRequest()));
                    System.out.println("Extracted Username: " + username);

                    return userService.findById(username)
                            .flatMap(user -> {
                                if (user == null) {
                                    System.out.println("User not found for ID: " + username);
                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                    return Mono.empty();
                                }
                                System.out.println("User found: " + user.getUsername());
                                exchange.getAttributes().put("userDetails", user);
                                return chain.filter(exchange);
                            });
                });
    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst("Authorization");
        return (bearerToken != null && bearerToken.startsWith("Bearer ")) ? bearerToken.substring(7) : null;
    }
}
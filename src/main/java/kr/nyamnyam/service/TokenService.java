package kr.nyamnyam.service;

import reactor.core.publisher.Mono;

public interface TokenService {
    Mono<String> createAndSaveToken(String userId, String role);
    Mono<Boolean> validateToken(String token);
    Mono<Void> logout(String token);
    Mono<String> refreshToken(String oldToken);
}

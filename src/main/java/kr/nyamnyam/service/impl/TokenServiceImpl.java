package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.Token;
import kr.nyamnyam.model.repository.TokenRepository;
import kr.nyamnyam.model.repository.UserRepository;
import kr.nyamnyam.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private long validityInMilliseconds; // 유효 기간 주입
    private final UserRepository userRepository;

    @Override
    public Mono<String> createAndSaveToken(String userId, String role) {
        return jwtTokenProvider.createToken(userId, role) // Mono<String> 반환
                .flatMap(token -> {
                    Token tokenEntity = Token.builder()
                            .userId(userId)
                            .token(token)
                            .expirationDate(new Date(System.currentTimeMillis() + validityInMilliseconds))
                            .isValid(true)
                            .build();
                    return tokenRepository.save(tokenEntity).then(Mono.just(token)); // 토큰 저장 후, 저장된 토큰 반환
                });
    }


    @Override
    public Mono<Boolean> validateToken(String token) {
        return tokenRepository.findByToken(token)
                .map(storedToken -> {
                    // 추가적인 유효성 검사 (예: 만료 여부)
                    return storedToken.getIsValid() && !storedToken.getExpirationDate().before(new Date());
                })
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<Void> logout(String token) {
        return tokenRepository.findByToken(token)
                .flatMap(storedToken -> {
                    storedToken.setIsValid(false); // 무효화 처리
                    return tokenRepository.save(storedToken);
                })
                .then(); // Mono<Void> 반환
    }

    @Override
    public Mono<String> refreshToken(String oldToken) {
        return tokenRepository.findByToken(oldToken)
                .flatMap(storedToken -> {
                    if (storedToken.getIsValid() && !storedToken.getExpirationDate().before(new Date())) {
                        // 사용자 역할을 UserRepository에서 조회
                        return userRepository.findById(storedToken.getUserId())
                                .flatMap(user -> jwtTokenProvider.createToken(user.getUsername(), user.getRole())
                                        .flatMap(newToken -> {
                                            storedToken.setIsValid(false);
                                            return tokenRepository.save(storedToken)
                                                    .then(tokenRepository.save(Token.builder()
                                                            .userId(storedToken.getUserId())
                                                            .token(newToken)
                                                            .expirationDate(new Date(System.currentTimeMillis() + validityInMilliseconds))
                                                            .isValid(true)
                                                            .build()))
                                                    .then(Mono.just(newToken));
                                        }));
                    } else {
                        return Mono.error(new RuntimeException("Invalid token for refresh"));
                    }
                });
    }

}

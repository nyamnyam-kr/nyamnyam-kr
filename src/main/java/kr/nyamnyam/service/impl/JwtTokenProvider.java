package kr.nyamnyam.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.validity.in.milliseconds}")
    private long validityInMilliseconds;

    // 비밀 키 생성 (직접 사용)
    private SecretKey generateKey() {
        // 비밀 키를 직접 사용하여 SecretKey 객체 생성
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Mono<String> createToken(String userId, String username, String role) {
        return Mono.fromCallable(() -> {
            try {
                Claims claims = Jwts.claims().setSubject(username);
                claims.put("role", role);
                claims.put("userId", userId);

                Date now = new Date();
                Date validity = new Date(now.getTime() + validityInMilliseconds);

                SecretKey key = generateKey();

                return Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(validity)
                        .signWith(key)
                        .compact();
            } catch (Exception e) {
                throw new RuntimeException("Error generating JWT key", e);
            }
        });
    }

    // JWT 토큰에서 사용자 정보 추출
    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(generateKey()) // 비밀 키 사용
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // JWT 토큰에서 사용자 권한 추출
    public String getRole(String token) {
        return (String) Jwts.parser()
                .setSigningKey(generateKey()) // 비밀 키 사용
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }

    // JWT 토큰에서 사용자 ID 추출
    public String getUserId(String token) {
        return (String) Jwts.parser()
                .setSigningKey(generateKey()) // 비밀 키 사용
                .parseClaimsJws(token)
                .getBody()
                .get("userId"); // userId 추출
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(generateKey())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

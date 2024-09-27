package kr.nyamnyam.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.validity.in.milliseconds}")
    private long validityInMilliseconds;

    // 비밀 키 생성
    private SecretKey generateKey() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] key = digest.digest(secretKey.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(key);
    }

    // JWT 토큰 생성 (비동기)
    public Mono<String> createToken(String username, String role) {
        return Mono.fromCallable(() -> {
            try {
                Claims claims = Jwts.claims().setSubject(username);
                claims.put("role", role);

                Date now = new Date();
                Date validity = new Date(now.getTime() + validityInMilliseconds);

                SecretKey key = generateKey();

                return Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(validity)
                        .signWith(key)
                        .compact();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error generating JWT key", e);
            }
        });
    }

    // JWT 토큰에서 사용자 정보 추출
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // JWT 토큰에서 사용자 권한 추출
    public String getRole(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("role");
    }

    // JWT 토큰의 유효성 및 만료 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.SignatureException | io.jsonwebtoken.ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}


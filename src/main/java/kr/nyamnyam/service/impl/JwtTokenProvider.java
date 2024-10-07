package kr.nyamnyam.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kr.nyamnyam.model.domain.User;
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

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Mono<String> createToken(User user) {
        return Mono.fromCallable(() -> {
            try {
                Claims claims = Jwts.claims().setSubject(user.getId());
                claims.put("role", user.getRole());
                claims.put("nickname", user.getNickname());
                claims.put("username", user.getUsername());

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



    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getRole(String token) {
        return (String) Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }


    public String getUserId(String token) {
        return (String) Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody()
                .get("userId");
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

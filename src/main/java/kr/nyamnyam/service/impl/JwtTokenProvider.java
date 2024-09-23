/*
package kr.nyamnyam.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private String secretKey = "your-secret-key"; // 비밀키는 환경변수로 관리하는 것이 안전함
    private long validityInMilliseconds = 3600000; // 1시간 유효기간

    // JWT 토큰 생성
    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username); // 사용자 정보 저장
        claims.put("role", role); // 사용자 권한 저장

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity) // 유효기간
                .signWith(SignatureAlgorithm.HS256, secretKey) // HMAC SHA256 서명
                .compact();
    }

    // JWT 토큰에서 사용자 정보 추출
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // JWT 토큰의 유효성 및 만료 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}*/

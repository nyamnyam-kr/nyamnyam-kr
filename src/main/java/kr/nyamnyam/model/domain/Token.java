package kr.nyamnyam.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "tokens")
@AllArgsConstructor
@Builder
public class Token {

    @Id
    private String id; // 토큰 ID
    private String userId; // 사용자 ID (User 컬렉션의 ID와 연결)
    private String token; // 발급된 JWT 토큰
    private Date expirationDate; // 토큰 만료 날짜
    private Boolean isValid; // 유효 여부
    private String username; // 사용자 이름 추가
    private String role; // 사용자 역할 추가
    private String nickname;
    private Long rating;


    public Token() {
        this.isValid = true; // 기본값은 유효
    }
}

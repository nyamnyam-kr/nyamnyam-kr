package kr.nyamnyam.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "tokens") // 컬렉션 이름 설정
@AllArgsConstructor
@Builder
public class Token {

    @Id
    private String id; // 토큰 ID
    private String userId; // 사용자 ID (User 컬렉션의 ID와 연결)
    private String token; // 발급된 JWT 토큰
    private Date expirationDate; // 토큰 만료 날짜
    private Boolean isValid; // 유효 여부

    // 기본 생성자도 추가할 수 있습니다.
    public Token() {
        this.isValid = true; // 기본값은 유효
    }
}

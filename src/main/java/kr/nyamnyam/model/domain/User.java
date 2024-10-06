package kr.nyamnyam.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users") // 컬렉션 이름 설정
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id; // Long에서 String으로 변경 (MongoDB의 ID 타입)
    private String username;
    private String password;
    private String nickname;
    private String name;
    private Long age;
    private String role;
    private String tel;
    private String gender;
    private Boolean enabled;
    private Long imgId;
    private Long rating;

}

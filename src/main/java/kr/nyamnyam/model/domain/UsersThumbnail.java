package kr.nyamnyam.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "usersThumbnails") // 컬렉션 이름 설정
@AllArgsConstructor
@Builder
public class UsersThumbnail {

    @Id
    private String id; // MongoDB에서 ID는 String 타입
    private String userId; // 해당 썸네일의 사용자 ID
    private String thumbnailUrl; // 썸네일 이미지 URL
    private Long createdAt; // 생성된 시간 (타임스탬프 혹은 다른 형식)
}

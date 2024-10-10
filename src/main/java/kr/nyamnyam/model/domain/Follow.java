package kr.nyamnyam.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "follows")
@AllArgsConstructor
@Builder
public class Follow {
    @Id
    private String id;
    private String followerId; // 팔로우하는 사용자 ID
    private String followeeId; // 팔로우되는 사용자 ID
}

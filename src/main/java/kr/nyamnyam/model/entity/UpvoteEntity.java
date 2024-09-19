package kr.nyamnyam.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "upvote")
public class UpvoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;
    private Long giveId; // 좋아요 누른 사용자 ID
    private Long haveId; // 좋아요 받은 사용자 ID

    // admin에서 넘어온거 아마 userId랑 haveId 같은 의미?
    private Long userId;

}

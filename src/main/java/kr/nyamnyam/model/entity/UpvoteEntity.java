package kr.nyamnyam.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Table(name = "upvote")
public class UpvoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;
    private Long giveId; // 좋아요 누른 사용자 ID
    private Long haveId; // 좋아요 받은 사용자 ID



}

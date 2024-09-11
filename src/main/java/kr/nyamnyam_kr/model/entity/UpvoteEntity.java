package kr.nyamnyam_kr.model.entity;

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
    private Long giveId;
    private Long haveId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;


}

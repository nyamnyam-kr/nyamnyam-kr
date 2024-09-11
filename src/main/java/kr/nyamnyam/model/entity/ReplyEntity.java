package kr.nyamnyam.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "replies")
public class ReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Date entryDate;
    private Date modifyDate;
    private Long upvoteId;
    private Long userId;
    private Long postId;

    // users, posts, restaurants JOIN 필요
}

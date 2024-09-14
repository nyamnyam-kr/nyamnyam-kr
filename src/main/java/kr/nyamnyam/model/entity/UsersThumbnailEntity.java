package kr.nyamnyam.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_thumbnails_entity")
public class UsersThumbnailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long thumbnailId;

    // userId 필드를 사용하여 유저를 참조
    @Column(name = "user_id")
    private Long userId;

    private String imagePath;

    private LocalDateTime createdAt;
}

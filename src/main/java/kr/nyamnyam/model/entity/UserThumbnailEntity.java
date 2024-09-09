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
public class UserThumbnailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thumbnail_id")
    private Long thumbnailId; // PK

    @Column(name = "image_path", nullable = false)
    private String imagePath; // 사진 경로

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // FK
    private UserEntity userEntity; // FK

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // 생성자
    public UserThumbnailEntity(UserEntity userEntity, String imagePath) {
        this.userEntity = userEntity;
        this.imagePath = imagePath;
        this.createdAt = LocalDateTime.now();
    }
}

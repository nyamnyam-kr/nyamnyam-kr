package kr.nyamnyam_kr.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    // 친절도, 맛, 청결 나눠야함
    private float rating;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "entry_date", updatable = false)
    private Date entryDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    @OneToMany(mappedBy = "post")
    private List<ReplyEntity> replyEntityList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    // 엔티티가 처음 저장될 때 실행
    @PrePersist
    protected void onCreate() {
        entryDate = new Date(); // 현재 시간을 entryDate로 설정
        modifyDate = new Date(); // 처음 생성될 때 modifyDate도 설정
    }

    // 엔티티가 수정될 때 실행
    @PreUpdate
    protected void onUpdate() {
        modifyDate = new Date(); // 현재 시간을 modifyDate로 설정
    }
}


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
@Table(name = "user_groups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String content;

    private Date entryDate;

    private Date modifiyDate;

    private Date dDay;

    private Long people;

    @OneToMany(mappedBy = "group")
    private List<AttendEntity> attendEntityList;

    @OneToMany(mappedBy = "group")
    private List<GroupReplyEntity> groupReplyEntityList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;


}

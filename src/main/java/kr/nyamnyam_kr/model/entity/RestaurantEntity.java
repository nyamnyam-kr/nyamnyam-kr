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
@Table(name = "restaurants")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private Long tel;

    private String operateTime;

    private Date entryDate;

    private Date modifyDate;

    private Long toilet;

    // !!! 은서 : zone이랑 Join 확인 부탁드려요
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", referencedColumnName = "id")
    private ZoneEntity zone;

    @OneToMany(mappedBy = "restaurant")
    private List<PostEntity> postEntityList;

    @OneToMany(mappedBy = "restaurant")
    private List<ReplyEntity> replyEntityList;

    @OneToMany(mappedBy = "restaurant")
    private List<MenuEntity> menuEntityList;

    @OneToMany(mappedBy = "restaurant")
    private List<GroupEntity> groupEntityList;

}

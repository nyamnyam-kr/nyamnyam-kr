package kr.nyamnyam.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "post")
public class PostEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name="content")
    private String content;

    @Column(name="rating")  // 친절도,맛,청결
    private float rating;

    @Column(name="entry_date")
    private Date entryDate;

    @Column(name="modify_date")
    private Date modifyDate;

    @OneToMany(mappedBy = "post")
    private List<ReplyEntity> replyEntityList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;




}

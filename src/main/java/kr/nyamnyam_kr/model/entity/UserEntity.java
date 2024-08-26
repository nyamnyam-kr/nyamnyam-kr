package kr.nyamnyam_kr.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="user")
public class UserEntity {

    @Id
    @GeneratedValue()
    @Column(name="id")
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="id")
    private String password;

    @Column(name="nickname")
    private String nickname;

    @Column(name="name")
    private String name;

    @Column(name="grade")
    private Long grade;

    @Column(name="role")
    private Long role;

    @Column(name="tel")
    private Long tel;

    @Column(name="gender")
    private Long gender;

    @Column(name="enabled")
    private Long enabled;

    @OneToMany(mappedBy="review")
    private List<ReviewEntity> reviewEntityList;

    @OneToMany(mappedBy="post")
    private List<PostEntity> postEntityList;



}

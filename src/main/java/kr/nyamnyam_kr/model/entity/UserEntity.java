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

    @Column(name="password")
    private String password;

    @Column(name="nickname")
    private String nickname;

    @Column(name="name")
    private String name;

    @Column(name="grade")
    private Long grade;

    @Column(name="role")        //role 시큐리티때문에[ 바꿈
    private String role;

    @Column(name="tel")
    private Long tel;

    @Column(name="gender")
    private Long gender;

    @Column(name="enabled")
    private Long enabled;

    @OneToMany(mappedBy="user")
    private List<ReplyEntity> replyEntityList;

    @OneToMany(mappedBy="user")
    private List<PostEntity> postEntityList;



}

package kr.nyamnyam.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name="user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="nickname")
    private String nickname;

    @Column(name="name")
    private String name;

    @Column(name="grade")
    private Long grade;

    @Column(name="role")
    private String role;

    @Column(name="tel")
    private String tel;

    @Column(name="gender")
    private String gender;

    @Column(name="enabled")
    private Boolean enabled;

    @OneToMany(mappedBy="user")
    private List<ReplyEntity> replyEntityList;

    @OneToMany(mappedBy="user")
    private List<PostEntity> postEntityList;

}

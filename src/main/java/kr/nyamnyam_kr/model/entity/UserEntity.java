package kr.nyamnyam_kr.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String name;

    private Long grade;
    //role 시큐리티때문에[ 바꿈
    private String role;

    private Long tel;

    private Long gender;

    private Long enabled;

    @OneToMany(mappedBy = "user")
    private List<ReplyEntity> replyEntityList;

    @OneToMany(mappedBy = "user")
    private List<PostEntity> postEntityList;


}

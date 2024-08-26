package kr.nyamnyam_kr.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserModel {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String name;
    private Long grade;
    private Long role;
    private Long tel;
    private Long gender;
    private Long enabled;

}

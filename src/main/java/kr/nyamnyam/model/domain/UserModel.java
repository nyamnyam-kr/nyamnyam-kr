package kr.nyamnyam.model.domain;

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
    private Long age;
    private String role;
    private String tel;
    private String gender;
    private Boolean enabled;
    private Long imgId;


}

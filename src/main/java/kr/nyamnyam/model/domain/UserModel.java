package kr.nyamnyam.model.domain;

import kr.nyamnyam.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class UserModel {


    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String name;
    private Long grade;
    private String role;
    private Long tel;
    private Long gender;
    private Long enabled;

    /*private List<GrantedAuthority> authorities;

    public List<GrantedAuthority> getAuthorities() {
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }*/

}

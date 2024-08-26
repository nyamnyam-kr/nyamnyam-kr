/*
package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.domain.UserModel;
import kr.nyamnyam_kr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService USER_SERVICE;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        USER_SERVICE = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Email: " + username);
        UserModel userModel = USER_SERVICE.findByUsername(username);
        System.out.println(userModel);
        if (userModel == null) {
            throw new UsernameNotFoundException(username + "is not a valid username");
        }

        return  userModel;
    }
}
*/

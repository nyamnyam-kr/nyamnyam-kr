package kr.nyamnyam.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.nyamnyam.model.domain.UserModel;
import kr.nyamnyam.model.entity.UsersEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean existsById(Long id);

    Optional<UsersEntity> findByUsername(String username);

    Optional<UsersEntity> findById(Long id);

    List<UsersEntity> findAll();

    long count();

    void deleteById(Long id);

    UsersEntity update(UserModel userModel);

    UsersEntity save(UserModel userModel);

    String loginWithOAuth2(String code, String receivedState, HttpServletRequest request);

    void startOAuth2(HttpServletRequest request, HttpServletResponse response) throws IOException;

    String authenticate(String username, String password);

    boolean validateToken(String token);

}

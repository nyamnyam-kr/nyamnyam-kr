package kr.nyamnyam_kr.service;

import kr.nyamnyam_kr.model.domain.UserModel;
import kr.nyamnyam_kr.model.entity.UserEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    UserEntity save(UserModel userModel);

    List<UserEntity> findAll();

    Optional<UserEntity> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);

    long count();

    //Map<?,?> login(UserModel model);

    UserModel login(String username,String password);

    /*Map<?, ?> login(UserModel model);*/

    Optional<UserEntity> findByUsernameAndPassword(String username, String password);


}

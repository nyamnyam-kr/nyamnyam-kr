package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.UserModel;
import kr.nyamnyam.model.entity.UsersEntity;

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

}

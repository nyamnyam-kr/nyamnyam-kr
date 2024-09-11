package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.UserModel;
import kr.nyamnyam.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserEntity> crawling(int pageNum, int pageSize);

    boolean existsById(Long id);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findById(Long id);

    List<UserEntity> findAll();

    long count();

    void deleteById(Long id);

    UserEntity update(UserModel userModel);

    UserEntity save(UserModel userModel);

}

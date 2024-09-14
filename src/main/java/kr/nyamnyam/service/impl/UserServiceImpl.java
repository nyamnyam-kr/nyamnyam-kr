package kr.nyamnyam.service.impl;


import kr.nyamnyam.model.domain.UserModel;
import kr.nyamnyam.model.entity.UsersEntity;
import kr.nyamnyam.model.repository.UserRepository;
import kr.nyamnyam.pattern.proxy.Pagination;
import kr.nyamnyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private Pagination pagination;

    private final UserRepository userRepository;

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Optional<UsersEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UsersEntity> findById(Long id) {
        return userRepository.findById(id);
    }


    @Override
    public List<UsersEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UsersEntity update(UserModel userModel) {
        return userRepository.findById(userModel.getId())
                .map(existingUser -> {
                    return UsersEntity.builder()
                            .id(existingUser.getId())
                            .username(Optional.ofNullable(userModel.getUsername()).orElse(existingUser.getUsername()))
                            .password(Optional.ofNullable(userModel.getPassword()).orElse(existingUser.getPassword()))
                            .nickname(Optional.ofNullable(userModel.getNickname()).orElse(existingUser.getNickname()))
                            .name(Optional.ofNullable(userModel.getName()).orElse(existingUser.getName()))
                            .age(Optional.ofNullable(userModel.getAge()).orElse(existingUser.getAge()))
                            .role(Optional.ofNullable(userModel.getRole()).orElse(existingUser.getRole()))
                            .tel(Optional.ofNullable(userModel.getTel()).orElse(existingUser.getTel()))
                            .gender(Optional.ofNullable(userModel.getGender()).orElse(existingUser.getGender()))
                            .enabled(Optional.ofNullable(userModel.getEnabled()).orElse(existingUser.getEnabled()))
                            .build();
                })
                .map(userRepository::save)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UsersEntity save(UserModel userModel) {
        UsersEntity usersEntity = UsersEntity.builder()
                .username(userModel.getUsername())
                .password(userModel.getPassword())
                .nickname(userModel.getNickname())
                .name(userModel.getName())
                .age(userModel.getAge())
                .role(userModel.getRole())
                .tel(userModel.getTel())
                .gender(userModel.getGender())
                .enabled(userModel.getEnabled())
                .build();
        return userRepository.save(usersEntity);
    }
}

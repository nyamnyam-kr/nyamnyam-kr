package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.domain.UserModel;
import kr.nyamnyam_kr.model.entity.UserEntity;
import kr.nyamnyam_kr.model.repository.UserRepository;
import kr.nyamnyam_kr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserEntity save(UserModel userModel) {
        UserEntity userEntity = UserEntity.builder()
                .username(userModel.getUsername())
                .password(userModel.getPassword())
                .nickname(userModel.getNickname())
                .name(userModel.getName())
                .grade(userModel.getGrade())
                .role(userModel.getRole())
                .tel(userModel.getTel())
                .gender(userModel.getGender())
                .enabled(userModel.getEnabled())
                .build();
        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public Map<?, ?> login(UserModel model) {
        Map<?,?> map = userRepository.login(model);

        return map;
    }

    /*public UserModel  findByUsername(String username) {
        Optional<UserEntity> byUserEmail = userRepository.findByUsername(username);
        System.out.println(byUserEmail);
        UserModel userModel = new UserModel();
        if (!byUserEmail.isPresent()) {
            return null;
        } else {
            return userModel;
        }
    }*/

}

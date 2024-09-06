package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.domain.UserModel;
import kr.nyamnyam_kr.model.entity.UserEntity;
import kr.nyamnyam_kr.model.repository.UserRepository;
import kr.nyamnyam_kr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity save(UserModel userModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userModel.getUsername());
        userEntity.setPassword(userModel.getPassword()); // 비밀번호 암호화
        userEntity.setNickname(userModel.getNickname());
        userEntity.setName(userModel.getName());
        userEntity.setGrade(userModel.getGrade());
        userEntity.setRole(userModel.getRole());
        userEntity.setTel(userModel.getTel());
        userEntity.setGender(userModel.getGender());
        userEntity.setEnabled(userModel.isEnabled());
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
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

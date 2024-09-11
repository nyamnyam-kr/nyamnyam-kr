package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.UserModel;
import kr.nyamnyam.model.entity.UserEntity;
import kr.nyamnyam.model.repository.UserRepository;
import kr.nyamnyam.service.UserService;
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

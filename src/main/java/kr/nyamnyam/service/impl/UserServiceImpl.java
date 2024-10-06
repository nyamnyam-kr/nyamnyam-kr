package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.Token;
import kr.nyamnyam.model.domain.User;
import kr.nyamnyam.model.repository.UserRepository;
import kr.nyamnyam.service.TokenService;
import kr.nyamnyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    @Override
    public Mono<Boolean> existsById(String id) {
        return userRepository.existsById(id);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<Long> count() {
        return userRepository.count();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<User> update(User user) {
        return userRepository.findById(user.getId())
                .flatMap(existingUser -> {
                    // 각 필드를 업데이트
                    if (user.getUsername() != null) existingUser.setUsername(user.getUsername());
                    if (user.getPassword() != null) existingUser.setPassword(user.getPassword());
                    if (user.getNickname() != null) existingUser.setNickname(user.getNickname());
                    if (user.getName() != null) existingUser.setName(user.getName());
                    if (user.getAge() != null) existingUser.setAge(user.getAge());
                    if (user.getRole() != null) existingUser.setRole(user.getRole());
                    if (user.getTel() != null) existingUser.setTel(user.getTel());
                    if (user.getGender() != null) existingUser.setGender(user.getGender());
                    if (user.getEnabled() != null) existingUser.setEnabled(user.getEnabled());

                    // 수정된 사용자 정보를 저장
                    return userRepository.save(existingUser);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }

    @Override
    public Mono<User> save(User user) {
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        User newUser = User.builder()
                .username(user.getUsername())
                .password(encodedPassword) // 암호화된 비밀번호 저장
                .nickname(user.getNickname())
                .name(user.getName())
                .age(user.getAge())
                .role(user.getRole())
                .tel(user.getTel())
                .gender(user.getGender())
                .enabled(user.getEnabled())
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public Mono<String> authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> new BCryptPasswordEncoder().matches(password, user.getPassword()))
                .flatMap(user -> tokenService.createAndSaveToken(user.getId())); // 사용자 ID로 토큰 생성
    }

}

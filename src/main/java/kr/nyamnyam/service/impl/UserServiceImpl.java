package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.User;
import kr.nyamnyam.model.repository.UserRepository;
import kr.nyamnyam.service.TokenService;
import kr.nyamnyam.service.UserService;
import kr.nyamnyam.service.UserThumbnailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;
    private final UserThumbnailService userThumbnailService;

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
    public Mono<User> update(User user, List<MultipartFile> thumbnails) {
        return userRepository.findById(user.getId())
                .flatMap(existingUser -> {
                    if (user.getUsername() != null) existingUser.setUsername(user.getUsername());
                    if (user.getPassword() != null) existingUser.setPassword(user.getPassword());
                    if (user.getNickname() != null) existingUser.setNickname(user.getNickname());
                    if (user.getName() != null) existingUser.setName(user.getName());
                    if (user.getAge() != null) existingUser.setAge(user.getAge());
                    if (user.getRole() != null) existingUser.setRole(user.getRole());
                    if (user.getTel() != null) existingUser.setTel(user.getTel());
                    if (user.getGender() != null) existingUser.setGender(user.getGender());
                    if (user.getEnabled() != null) existingUser.setEnabled(user.getEnabled());

                    // 썸네일 업데이트 처리
                    userThumbnailService.uploadThumbnail(existingUser, thumbnails);

                    return userRepository.save(existingUser);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }


    @Override
    public Mono<User> save(User user, List<MultipartFile> thumbnails) {
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        User newUser = User.builder()
                .username(user.getUsername())
                .password(encodedPassword)
                .nickname(user.getNickname())
                .name(user.getName())
                .age(user.getAge())
                .role(user.getRole())
                .tel(user.getTel())
                .gender(user.getGender())
                .enabled(user.getEnabled())
                .score(36.5)
                .build();

        return userRepository.save(newUser)
                .doOnSuccess(savedUser -> {
                    // 썸네일 저장
                    userThumbnailService.uploadThumbnail(savedUser, thumbnails);
                });
    }

    @Override
    public Mono<String> authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> new BCryptPasswordEncoder().matches(password, user.getPassword()))
                .flatMap(user -> tokenService.createAndSaveToken(user.getId()));
    }

}

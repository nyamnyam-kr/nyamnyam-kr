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
                    existingUser.setUsername(user.getUsername() != null ? user.getUsername() : existingUser.getUsername());
                    existingUser.setPassword(user.getPassword() != null ? user.getPassword() : existingUser.getPassword());
                    existingUser.setNickname(user.getNickname() != null ? user.getNickname() : existingUser.getNickname());
                    existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
                    existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
                    existingUser.setRole(user.getRole() != null ? user.getRole() : existingUser.getRole());
                    existingUser.setTel(user.getTel() != null ? user.getTel() : existingUser.getTel());
                    existingUser.setGender(user.getGender() != null ? user.getGender() : existingUser.getGender());
                    existingUser.setEnabled(user.getEnabled() != null ? user.getEnabled() : existingUser.getEnabled());

                    return userThumbnailService.uploadThumbnail(existingUser, thumbnails)
                            .then(userRepository.save(existingUser));
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }

    @Override
    public Mono<User> save(User user, List<MultipartFile> thumbnails) {
        return userRepository.findByUsername(user.getUsername())
                .flatMap(existingUser -> Mono.<User>error(new RuntimeException("Username is already taken.")))
                .switchIfEmpty(Mono.defer(() -> {
                    String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
                    User newUser = User.builder()
                            .username(user.getUsername())
                            .password(encodedPassword)
                            .nickname(user.getNickname())
                            .name(user.getName())
                            .age(user.getAge())
                            .role("USER")
                            .tel(user.getTel())
                            .gender(user.getGender())
                            .enabled(true)
                            .score(36.5)
                            .build();

                    return userRepository.save(newUser)
                            .flatMap(savedUser -> userThumbnailService.uploadThumbnail(savedUser, thumbnails)
                                    .flatMap(thumbnailIds -> {
                                        savedUser.setImgId(thumbnailIds.isEmpty() ? null : thumbnailIds.get(0));
                                        return userRepository.save(savedUser); // Mono<User> 반환이 기대됨
                                    }));
                }));
    }

    @Override
    public Mono<String> authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid username or password."))) // username이 없는 경우
                .filter(user -> new BCryptPasswordEncoder().matches(password, user.getPassword()))
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid username or password."))) // password가 틀린 경우
                .flatMap(user -> {
                    if (Boolean.FALSE.equals(user.getEnabled())) {
                        return Mono.error(new RuntimeException("Account is disabled"));
                    }
                    return tokenService.createAndSaveToken(user.getId());
                });
    }



    @Override
    public Mono<User> enable(String targetUserId) {
        return userRepository.findById(targetUserId)
                .flatMap(user -> {
                    user.setEnabled(!user.getEnabled());
                    return userRepository.save(user);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }
}

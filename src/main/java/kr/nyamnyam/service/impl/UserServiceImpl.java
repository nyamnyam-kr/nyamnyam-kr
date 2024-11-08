package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.User;
import kr.nyamnyam.model.repository.UserRepository;
import kr.nyamnyam.service.TokenService;
import kr.nyamnyam.service.UserService;
import kr.nyamnyam.service.UserThumbnailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
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
        return userRepository.existsById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))); // 404
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))); // 404
    }

    @Override
    public Mono<User> findById(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))); // 404
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
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))) // 404
                .flatMap(existingUser -> userRepository.deleteById(id));
    }

    @Override
    public Mono<User> update(User user, List<MultipartFile> thumbnails) {
        return userRepository.findById(user.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))) // 404
                .flatMap(existingUser -> {
                    existingUser.setUsername(user.getUsername() != null ? user.getUsername() : existingUser.getUsername());
                    if (user.getPassword() != null) {
                        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
                        existingUser.setPassword(encodedPassword);
                    }
                    existingUser.setNickname(user.getNickname() != null ? user.getNickname() : existingUser.getNickname());
                    existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
                    existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
                    existingUser.setRole(user.getRole() != null ? user.getRole() : existingUser.getRole());
                    existingUser.setTel(user.getTel() != null ? user.getTel() : existingUser.getTel());
                    existingUser.setGender(user.getGender() != null ? user.getGender() : existingUser.getGender());
                    existingUser.setEnabled(user.getEnabled() != null ? user.getEnabled() : existingUser.getEnabled());

                    return userThumbnailService.uploadThumbnail(existingUser.getId(), thumbnails)
                            .then(userRepository.save(existingUser));
                });
    }


    @Override
    public Mono<User> save(User user) {
        return userRepository.findByUsername(user.getUsername())
                .flatMap(existingUser -> Mono.<User>error(new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken."))) // 409 Conflict
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
                            .imgId(null)
                            .build();

                    return userRepository.save(newUser);
                }));
    }

    @Override
    public Mono<User> updateImgIdOnly(User user) {
        return userRepository.findById(user.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))) // 404
                .flatMap(existingUser -> {
                    existingUser.setImgId(user.getImgId());
                    return userRepository.save(existingUser);
                });
    }

    @Override
    public Mono<String> authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"))) // 401 Unauthorized
                .filter(user -> new BCryptPasswordEncoder().matches(password, user.getPassword()))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"))) // 401 Unauthorized
                .flatMap(user -> {
                    if (Boolean.FALSE.equals(user.getEnabled())) {
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Account is disabled")); // 403 Forbidden
                    }
                    return tokenService.createAndSaveToken(user.getId()); // 인증 성공 시 토큰 생성
                });
    }

    @Override
    public Mono<User> setEnableStatus(String userId, Boolean enabled) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))) // 404
                .flatMap(user -> {
                    user.setEnabled(enabled);
                    return userRepository.save(user);
                });
    }
}

package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.User;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {

    Mono<Boolean> existsById(String id);

    Mono<User> findByUsername(String username);

    Mono<User> findById(String id);

    Flux<User> findAll();

    Mono<Long> count();

    Mono<Void> deleteById(String id);

    Mono<User> update(User user, List<MultipartFile> thumbnails);

    Mono<User> save(User user, List<MultipartFile> thumbnails);

    Mono<String> authenticate(String username, String password);

    Mono<User> enable(String targetUserId);

}

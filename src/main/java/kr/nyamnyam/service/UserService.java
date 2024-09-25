package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.User; // User로 변경
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<Boolean> existsById(String id); // Long에서 String으로 변경

    Mono<User> findByUsername(String username); // Optional<User> -> Mono<User>로 변경

    Mono<User> findById(String id); // Optional<User> -> Mono<User>로 변경

    Flux<User> findAll(); // UsersEntity에서 User로 변경

    Mono<Long> count(); // long -> Mono<Long>으로 변경

    Mono<Void> deleteById(String id); // Long에서 String으로 변경

    Mono<User> update(User user); // UsersEntity에서 User로 변경

    Mono<User> save(User user); // UsersEntity에서 User로 변경

    Mono<String> authenticate(String username, String password); // String -> Mono<String>으로 변경

    Mono<Boolean> validateToken(String token); // boolean -> Mono<Boolean>으로 변경
}

package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.domain.User; // UsersEntity를 User로 변경
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> { // JpaRepository -> MongoRepository로 변경, Long -> String으로 변경
    Mono<User> findByUsername(String username); // Optional<User> -> Mono<User>로 변경
}

package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.MessageEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MessageRepository extends ReactiveMongoRepository<MessageEntity, String> {

    @Tailable //커서를 안닫고 계속 유지한다.
    @Query("{sender: ?0,receiver: ?1}")
    Flux<MessageEntity> mFindBySender(String sender, String receiver);// Flux 흐름 response를 유지하면서 계속 흘려보내기
}

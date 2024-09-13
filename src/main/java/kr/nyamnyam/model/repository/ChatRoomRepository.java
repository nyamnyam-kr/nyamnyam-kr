package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.domain.ChatRoom;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRoomRepository extends ReactiveMongoRepository<ChatRoom, String> {


}

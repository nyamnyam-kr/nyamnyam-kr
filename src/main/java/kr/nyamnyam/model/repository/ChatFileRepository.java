package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.domain.Chat;
import kr.nyamnyam.model.domain.ChatFile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatFileRepository extends ReactiveMongoRepository<ChatFile, String> {

    // 특정 채팅방의 파일 스트림
    @Tailable
    Flux<ChatFile> findByChatRoomId(String chatRoomId);

    // 특정 사용자가 보낸 파일 스트림
    @Tailable
    Flux<ChatFile> findBySenderAndChatRoomId(String sender, String chatRoomId);
}

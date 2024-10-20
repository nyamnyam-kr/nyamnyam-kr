package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.ChatRoom;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {

    Mono<ChatRoom> save(ChatRoom chatRoom);

    Flux<ChatRoom> findAllByNickname(String nickname);

    Mono<ChatRoom> findById(String id);

    public Mono<ChatRoom> updateChatRoom(String id, String nickname);

    Mono<Void> deleteById(String id);

    Mono<Boolean> existsById(String id);

    Mono<Long> count();


    Flux<ChatRoom> crawling();



}

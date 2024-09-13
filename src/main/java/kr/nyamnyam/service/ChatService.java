package kr.nyamnyam.service;


import kr.nyamnyam.model.domain.Chat;
import kr.nyamnyam.model.domain.ChatRoom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatService {
    Flux<Chat> mFindBySender(String sender, String chatRoomId);

    Flux<Chat> mFindByChatRoomId(String chatRoomId);

    Mono<Chat> saveMessage(Chat chat);

}

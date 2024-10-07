package kr.nyamnyam.service;


import kr.nyamnyam.model.domain.Chat;
import kr.nyamnyam.model.domain.ChatRoom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface ChatService {
    Flux<Chat> mFindBySender(String sender, String chatRoomId);

    Flux<Chat> mFindByChatRoomId(String chatRoomId);

    Mono<Chat> saveMessage(Chat chat);


    Mono<Long> getUnreadMessageCountByChatRoomId(String chatRoomId, String nickname);

    Mono<Long> getParticipantsNotReadCount(String chatId);

    Mono<Chat> markAsRead(String chatId, String nickname);

    Mono<Chat> updateReadBy(String chatId, String nickname);

}

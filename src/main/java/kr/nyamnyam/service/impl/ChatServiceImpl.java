package kr.nyamnyam.service.impl;


import kr.nyamnyam.model.domain.Chat;
import kr.nyamnyam.model.domain.ChatRoom;
import kr.nyamnyam.model.repository.ChatRepository;
import kr.nyamnyam.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Flux<Chat> mFindBySender(String sender, String chatRoomId) {
        return chatRepository.mFindBySender(sender, chatRoomId)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<Chat> mFindByChatRoomId(String chatRoomId) {
        return chatRepository.mFindByChannelId(chatRoomId)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Chat> saveMessage(Chat chat) {
        return chatRepository.save(chat);
    }
}

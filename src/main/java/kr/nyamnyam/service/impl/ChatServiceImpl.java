package kr.nyamnyam.service.impl;


import kr.nyamnyam.model.domain.Chat;
import kr.nyamnyam.model.domain.ChatFile;
import kr.nyamnyam.model.domain.ChatRoom;
import kr.nyamnyam.model.repository.ChatFileRepository;
import kr.nyamnyam.model.repository.ChatRepository;
import kr.nyamnyam.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RequiredArgsConstructor
@Service
public
class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatFileRepository chatFileRepository;

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

    @Override
    public Mono<Chat> uploadFileAndSaveMessage(Chat chat) {
        // 파일들을 저장하고 메시지를 저장하는 로직
        List<ChatFile> files = chat.getFiles();
        return Flux.fromIterable(files)
                .flatMap(chatFileRepository::save) // 각 파일을 저장
                .then(chatRepository.save(chat)); // 파일 저장이 완료되면 메시지 저장
    }
}

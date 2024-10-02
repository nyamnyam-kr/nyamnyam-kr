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
public class ChatServiceImpl implements ChatService {

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

    @Override
    public Mono<Long> getUnreadMessageCountByChatRoomId(String chatRoomId, String nickname) {
        return chatRepository.findByChatRoomId(chatRoomId)
                .collectList() // 모든 메시지를 리스트로 가져옴
                .map(chats -> {
                    long unreadCount = chats.stream()
                            .filter(chat -> !Boolean.TRUE.equals(chat.getReadBy().get(nickname))) // 해당 사용자가 읽지 않은 메시지 필터링
                            .count();
                    return unreadCount; // 읽지 않은 메시지 수 반환
                });
    }

    @Override
    public Mono<Long> getParticipantsNotReadCount(String chatId) {
        return chatRepository.findById(chatId)
                .map(chat -> {
                    long totalParticipants = chat.getTotalParticipants() != null ? chat.getTotalParticipants() : 0;
                    long readCount = chat.getReadBy().values().stream()
                            .filter(Boolean::booleanValue) // 읽은 메시지 수
                            .count();
                    return totalParticipants - readCount; // 전체 참가자 수에서 읽은 수를 뺌
                });
    }

}
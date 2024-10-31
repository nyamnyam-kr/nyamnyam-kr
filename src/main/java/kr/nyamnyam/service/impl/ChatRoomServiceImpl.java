package kr.nyamnyam.service.impl;


import kr.nyamnyam.model.domain.Chat;
import kr.nyamnyam.model.domain.ChatRoom;
import kr.nyamnyam.model.repository.ChatRoomRepository;
import kr.nyamnyam.pattern.proxy.Pagination;
import kr.nyamnyam.service.ChatRoomService;
import lombok.RequiredArgsConstructor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;


    @Override
    public Mono<ChatRoom> save(ChatRoom chatRoom) {
        // 참가자 목록 정렬
        chatRoom.setParticipants(
                chatRoom.getParticipants().stream().sorted().toList()
        );
        // 정렬된 참가자 목록으로 채팅방 저장
        return chatRoomRepository.save(chatRoom);
    }


    @Override
    public Mono<ChatRoom> findByParticipants(List<String> participants) {
        // 참가자 목록 정렬
        List<String> sortedParticipants = participants.stream().sorted().toList();

        // 정렬된 목록으로 채팅방 조회
        return chatRoomRepository.findByParticipants(sortedParticipants)
                .switchIfEmpty(Mono.empty()); // 존재하지 않으면 Mono.empty() 반환
    }

    @Override
    public Flux<ChatRoom> findAllByNickname(String nickname) {
        // nickname이 null이면 전체 ChatRoom 조회, 아니면 nickname으로 필터링하여 조회
        if (nickname == null || nickname.isEmpty()) {
            return chatRoomRepository.findAll();
        }
        return chatRoomRepository.findByParticipantsContains(nickname);
    }


    @Override
    public Mono<ChatRoom> findById(String id) {
        return chatRoomRepository.findById(id);
    }

    @Override
    public Mono<ChatRoom> updateChatRoom(String id, String nickname) {
        return chatRoomRepository.findById(id)
                .flatMap(existingChatRoom -> {
                    // 해당 참가자를 participants에서 제거
                    existingChatRoom.setParticipants(
                            existingChatRoom.getParticipants().stream()
                                    .filter(participant -> !participant.equals(nickname))
                                    .collect(Collectors.toList())
                    );

                    // 참가자가 1명 이하로 남으면 채팅방 삭제
                    if (existingChatRoom.getParticipants().size() <= 1) {
                        return chatRoomRepository.deleteById(id).then(Mono.empty());
                    } else {
                        return chatRoomRepository.save(existingChatRoom);
                    }
                });
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return chatRoomRepository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return chatRoomRepository.existsById(id);
    }

    @Override
    public Mono<Long> count() {
        return chatRoomRepository.count();
    }

    @Override
    public Flux<ChatRoom> crawling() {
        return null;
    }



}
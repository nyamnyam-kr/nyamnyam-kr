package kr.nyamnyam.service.impl;


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

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Mono<ChatRoom> save(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public Flux<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }

    @Override
    public Mono<ChatRoom> findById(String id) {
        return chatRoomRepository.findById(id);
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




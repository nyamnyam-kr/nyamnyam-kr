package kr.nyamnyam.controller;


import kr.nyamnyam.model.domain.Chat;
import kr.nyamnyam.model.repository.ChatRepository;
import kr.nyamnyam.model.repository.ChatRoomRepository;
import kr.nyamnyam.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;
    private final ChatRepository chatRepository;


    // 1대1??
    @CrossOrigin
    @GetMapping(value = "/sender/{sender}/chatroom/{chatRoomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMessage(@PathVariable String sender, @PathVariable String chatRoomId) {

        return chatService.mFindBySender(sender, chatRoomId).subscribeOn(Schedulers.boundedElastic());
    }

    // 채팅 방에서 메세지를 보내면 저장하는 친구
    @CrossOrigin
    @PostMapping("/{chatRoomId}")
    public Mono<Chat> setMessage(@RequestBody Chat chat, @PathVariable String chatRoomId) {
        chat.setCreatedAt(LocalDateTime.now());
        chat.setChatRoomId(chatRoomId);

        return chatService.saveMessage(chat);
    }

    //얘는 보낸 메세지를 바로 채널에다가  뿌려주는 친구
    @CrossOrigin
    @GetMapping(value = "/{chatRoomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMessageByChannel(@PathVariable String chatRoomId) {
       return chatService.mFindByChatRoomId(chatRoomId).subscribeOn(Schedulers.boundedElastic());
    }

}

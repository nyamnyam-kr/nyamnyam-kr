package kr.nyamnyam.controller;


import kr.nyamnyam.model.domain.Chat;
import kr.nyamnyam.model.domain.ChatFile;
import kr.nyamnyam.model.repository.ChatRepository;
import kr.nyamnyam.model.repository.ChatRoomRepository;
import kr.nyamnyam.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*") // 모든 출처 허용
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;
    private final ChatRepository chatRepository;


    // 1대1??
    @GetMapping(value = "/sender/{sender}/chatroom/{chatRoomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMessage(@PathVariable String sender, @PathVariable String chatRoomId) {

        return chatService.mFindBySender(sender, chatRoomId).subscribeOn(Schedulers.boundedElastic());
    }

    // 채팅 방에서 메세지를 보내면 저장하는 친구
    @PostMapping("/{chatRoomId}")
    public Mono<Chat> setMessage(@RequestBody Chat chat, @PathVariable String chatRoomId) {
        chat.setCreatedAt(LocalDateTime.now());
        chat.setChatRoomId(chatRoomId);

        return chatService.saveMessage(chat);
    }

    //얘는 보낸 메세지를 바로 채널에다가  뿌려주는 친구
    @GetMapping(value = "/{chatRoomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMessageByChannel(@PathVariable String chatRoomId) {
        return chatService.mFindByChatRoomId(chatRoomId).subscribeOn(Schedulers.boundedElastic());
    }



    @PostMapping(value = "/{chatRoomId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Chat> uploadFileAndPostMessage(@RequestBody Chat chat, @PathVariable String chatRoomId) {
        chat.setChatRoomId(chatRoomId);
        chat.setCreatedAt(LocalDateTime.now());
        return chatService.uploadFileAndSaveMessage(chat);
    }

    // 채팅방 별 읽지 않은 메시지 수
    @GetMapping("/{chatRoomId}/unreadCount/{nickname}")
    public Mono<Long> getUnreadCount(@PathVariable String chatRoomId, @PathVariable String nickname) {
        return chatService.getUnreadMessageCountByChatRoomId(chatRoomId, nickname);
    }

    // 특정 메시지에서 읽지 않은 참가자 수
    @GetMapping("/{chatId}/notReadParticipantsCount")
    public Mono<Long> getNotReadParticipantsCount(@PathVariable String chatId) {
        return chatService.getParticipantsNotReadCount(chatId);
    }

}
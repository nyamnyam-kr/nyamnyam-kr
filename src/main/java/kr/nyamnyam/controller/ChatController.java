package kr.nyamnyam.controller;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import kr.nyamnyam.model.domain.Chat;
import kr.nyamnyam.service.ChatRoomService;
import kr.nyamnyam.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;
    private final ChatRoomService chatRoomService;
    private static final String SECRET_KEY = "JWT_SECRET_KEY=bywm4zC5-vR36j_mZPsd4jmNFUuny0XuYoln59AStsI="; // 실제 비밀 키로 변경하세요

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

        // 채팅방 ID로 채팅방 정보를 가져옴
        return chatRoomService.findById(chatRoomId)
                .map(chatRoom -> {
                    // 참가자 수를 가져와서 chat 객체에 설정
                    chat.setTotalParticipants(chatRoom.getParticipants().stream().count());
                    return chat; // 처리한 chat 객체 반환
                })
                .flatMap(chatService::saveMessage); // 채팅 메시지 저장
    }

    //얘는 보낸 메세지를 바로 채널에다가  뿌려주는 친구
    @GetMapping(value = "/{chatRoomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMessageByChannel(@PathVariable String chatRoomId, @RequestParam String token) {
        // token을 사용하여 필요한 로직 처리
        // 예: token을 검증하거나, 사용자 정보를 가져오는 등



        return chatService.mFindByChatRoomId(chatRoomId).subscribeOn(Schedulers.boundedElastic());
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

    @PatchMapping("/{chatId}/read/{nickname}")
    public Mono<Chat> markMessageAsRead(@PathVariable String chatId, @PathVariable String nickname) {
        return chatService.markAsRead(chatId, nickname);
    }

    @PutMapping("/{chatId}/read/{nickname}")
    public Mono<ResponseEntity<Chat>> updateReadBy(@PathVariable String chatId, @PathVariable String nickname) {
        return chatService.updateReadBy(chatId, nickname)
                .map(updatedChat -> ResponseEntity.ok(updatedChat))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 파일 업로드 엔드포인트
    @PostMapping("/uploads")
    public Mono<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        return chatService.uploadFile(file)
                .map(url -> {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("file", true);
                    resultMap.put("url", url);
                    return resultMap;
                })
                .onErrorResume(e -> {
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("file", false);
                    errorResponse.put("error", e.getMessage());
                    return Mono.just(errorResponse);
                });
    }


}
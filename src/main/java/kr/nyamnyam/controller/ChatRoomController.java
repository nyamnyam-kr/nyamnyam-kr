package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.ChatRoom;
import kr.nyamnyam.service.ChatRoomService;
import kr.nyamnyam.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

//Post, Put Delete  Get

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatRoom")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;


    @PostMapping("/save")
    public Mono<ResponseEntity<ChatRoom>> save(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.save(chatRoom)
                .map(savedChatRoom -> ResponseEntity.ok(savedChatRoom))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


    @GetMapping("/findAll/{nickname}")
    public Flux<ChatRoom> findAll( @PathVariable String nickname) {

        // Service 레이어로 nickname 전달
        return chatRoomService.findAllByNickname(nickname);
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<ChatRoom>> findById(@PathVariable String id) {
        return chatRoomService.findById(id)
                .map(chatRoom -> ResponseEntity.ok(chatRoom))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/leaveChatRoom/{id}/{nickname}")
    public Mono<ResponseEntity<Void>> leaveChatRoom(@PathVariable String id, @PathVariable String nickname) {
        return chatRoomService.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        // 참가자 나가기 로직 호출
                        return chatRoomService.updateChatRoom(id, nickname)
                                .then(Mono.just(ResponseEntity.ok().<Void>build()));
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }


    @GetMapping("/existsById/{id}")
    public Mono<ResponseEntity<Boolean>> existsById(@PathVariable String id) {
        return chatRoomService.existsById(id)
                .map(exists -> ResponseEntity.ok(exists));
    }


    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> count() {
        return chatRoomService.count()
                .map(count -> ResponseEntity.ok(count));
    }





}
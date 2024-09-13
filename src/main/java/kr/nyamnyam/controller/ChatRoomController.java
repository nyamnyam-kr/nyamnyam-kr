package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.ChatRoom;
import kr.nyamnyam.service.ChatRoomService;
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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/chatRoom")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;


    @PostMapping("/save")
    public Mono<ResponseEntity<ChatRoom>> save(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.save(chatRoom)
                .map(savedChatRoom -> ResponseEntity.ok(savedChatRoom))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


    @GetMapping("/findAll")
    public Flux<ChatRoom> findAll() {
        return chatRoomService.findAll();
    }


    @GetMapping("/findById/{id}")
    public Mono<ResponseEntity<ChatRoom>> findById(@PathVariable String id) {
        return chatRoomService.findById(id)
                .map(chatRoom -> ResponseEntity.ok(chatRoom))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/deleteById/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
        return chatRoomService.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return chatRoomService.deleteById(id)
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


    public Flux<ChatRoom> crawling() {
        return null;
    }
}

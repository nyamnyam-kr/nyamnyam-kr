package kr.nyamnyam.controller;


import kr.nyamnyam.model.entity.MessageEntity;
import kr.nyamnyam.model.repository.MessageRepository;
import kr.nyamnyam.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class MessageController {

    private final MessageService messageService;

    @GetMapping(value = "/sender/{sender}/receiver/{receiver}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessageEntity> getMessage(@PathVariable String sender, @PathVariable String receiver) {
        return messageService.getMessagesBySenderAndReceiver(sender, receiver);
    }

    @PostMapping("/chat")
    public Mono<MessageEntity> setMessage(@RequestBody MessageEntity message) {
        return messageService.saveMessage(message);
    }

}

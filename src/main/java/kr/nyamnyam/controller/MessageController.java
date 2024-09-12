package kr.nyamnyam.controller;


import kr.nyamnyam.model.entity.MessageEntity;
import kr.nyamnyam.model.repository.MessageRepository;
import kr.nyamnyam.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app/messages")
public class MessageController {

    private final MessageService messageService;
    private final MessageRepository messageRepository;


    // 1대1??
    @CrossOrigin
    @GetMapping(value = "/sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessageEntity> getMessage(@PathVariable String sender, @PathVariable String receiver) {
        return messageRepository.mFindBySender(sender, receiver).subscribeOn(Schedulers.boundedElastic());
    }

    // 채팅 방에서 메세지를 보내면 저장하는 친구
    @PostMapping("/chat")
    public Mono<MessageEntity> setMessage(@RequestBody MessageEntity message) {
        message.setCreatedAt(LocalDateTime.now());
        return messageService.saveMessage(message);
    }

    //얘는 보낸 메세지를 바로 채널에다가  뿌려주는 친구
    @CrossOrigin
    @GetMapping(value = "/channelId/{channelId}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessageEntity> getMessageByChannel(@PathVariable String channelId) {
        return messageRepository.mFindByChannelId(channelId).subscribeOn(Schedulers.boundedElastic());
    }

}

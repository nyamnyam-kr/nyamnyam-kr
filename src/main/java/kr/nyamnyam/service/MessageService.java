package kr.nyamnyam.service;


import kr.nyamnyam.model.entity.MessageEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageService {
    Flux<MessageEntity> getMessagesBySenderAndReceiver(String sender, String receiver);
    Mono<MessageEntity> saveMessage(MessageEntity message);

}

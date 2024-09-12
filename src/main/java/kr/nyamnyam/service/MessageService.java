package kr.nyamnyam.service;


import kr.nyamnyam.model.entity.MessageEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageService {
    Flux<MessageEntity> mFindBySender(String sender, String channelId);
    Flux<MessageEntity> mFindByChannelId(String channelId);

    Mono<MessageEntity> saveMessage(MessageEntity message);

}

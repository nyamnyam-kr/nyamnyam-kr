package kr.nyamnyam.service.impl;


import kr.nyamnyam.model.entity.MessageEntity;
import kr.nyamnyam.model.repository.MessageRepository;
import kr.nyamnyam.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public Flux<MessageEntity> mFindBySender(String sender, String channerId) {
        return messageRepository.mFindBySender(sender, channerId)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<MessageEntity> mFindByChannelId(String channelId) {
        return messageRepository.mFindByChannelId(channelId)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<MessageEntity> saveMessage(MessageEntity message) {
        return messageRepository.save(message);
    }
}

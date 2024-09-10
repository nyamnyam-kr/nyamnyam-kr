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
    public Flux<MessageEntity> getMessagesBySenderAndReceiver(String sender, String receiver) {
        return messageRepository.mFindBySender(sender, receiver)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<MessageEntity> saveMessage(MessageEntity message) {
        return messageRepository.save(message);
    }
}

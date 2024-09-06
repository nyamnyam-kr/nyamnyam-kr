package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.MessageModel;
import kr.nyamnyam.model.entity.MessageEntity;
import kr.nyamnyam.model.repository.MessageRepository;
import kr.nyamnyam.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public MessageEntity save(MessageEntity messageEntity) {
        return messageRepository.save(messageEntity);
    }

    @Override
    public List<MessageEntity> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<MessageEntity> findById(String id) {
        return messageRepository.findById(id);
    }

    @Override
    public boolean deleteById(String id) {
        if (existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public boolean existsById(String id) {
        return messageRepository.existsById(id);
    }

    @Override
    public long count() {
        return messageRepository.count();
    }
}

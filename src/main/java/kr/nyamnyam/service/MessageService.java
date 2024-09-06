package kr.nyamnyam.service;


import kr.nyamnyam.model.domain.MessageModel;
import kr.nyamnyam.model.entity.MessageEntity;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    MessageEntity save(MessageEntity messageEntity);

    List<MessageEntity> findAll();

    Optional<MessageEntity> findById(String id);

    boolean deleteById(String id);

    boolean existsById(String id);

    long count();
}

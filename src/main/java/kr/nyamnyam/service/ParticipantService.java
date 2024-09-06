package kr.nyamnyam.service;


import kr.nyamnyam.model.entity.ParticipantEntity;

import java.util.List;
import java.util.Optional;

public interface ParticipantService {
    ParticipantEntity save(ParticipantEntity participantEntity);

    List<ParticipantEntity> findAll();

    Optional<ParticipantEntity> findById(String id);

    boolean deleteById(String id);

    boolean existsById(String id);

    long count();
}

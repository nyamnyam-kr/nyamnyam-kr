package kr.nyamnyam.service;


import kr.nyamnyam.model.domain.Participant;

import java.util.List;
import java.util.Optional;

public interface ParticipantService {
    Participant save(Participant participant);

    List<Participant> findAll();

    Optional<Participant> findById(String id);

    boolean deleteById(String id);

    boolean existsById(String id);

    long count();
}

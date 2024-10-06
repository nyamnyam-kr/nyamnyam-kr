package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.Participant;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParticipantService {
    Mono<Participant> save(Participant participant);

    Flux<Participant> findAll();

    Mono<Participant> findById(String id);

    Mono<Boolean> deleteById(String id);

    Mono<Boolean> existsById(String id);

    Mono<Long> count();
}

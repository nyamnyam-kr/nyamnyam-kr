package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.Participant;
import kr.nyamnyam.model.repository.ParticipantRepository;
import kr.nyamnyam.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository participantRepository;

    @Override
    public Mono<Participant> save(Participant participant) {
        return participantRepository.save(participant);
    }

    @Override
    public Flux<Participant> findAll() {
        return participantRepository.findAll();
    }

    @Override
    public Mono<Participant> findById(String id) {
        return participantRepository.findById(id);
    }

    @Override
    public Mono<Boolean> deleteById(String id) {
        return existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return participantRepository.deleteById(id).then(Mono.just(true));
                    }
                    return Mono.just(false);
                });
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return participantRepository.existsById(id);
    }

    @Override
    public Mono<Long> count() {
        return participantRepository.count();
    }
}

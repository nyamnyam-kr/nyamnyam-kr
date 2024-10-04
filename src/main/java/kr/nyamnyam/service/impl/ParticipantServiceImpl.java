package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.Participant;
import kr.nyamnyam.model.repository.ParticipantRepository;
import kr.nyamnyam.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository participantRepository;

    @Override
    public Participant save(Participant participant) {
        return participantRepository.save(participant);
    }

    @Override
    public List<Participant> findAll() {
        return participantRepository.findAll();
    }

    @Override
    public Optional<Participant> findById(String id) {
        return participantRepository.findById(id);
    }

    @Override
    public boolean deleteById(String id) {
        if (existsById(id)) {
            participantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsById(String id) {
        return participantRepository.existsById(id);
    }

    @Override
    public long count() {
        return participantRepository.count();
    }
}

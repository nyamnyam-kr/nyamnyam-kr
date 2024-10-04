package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.domain.Participant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends MongoRepository<Participant, String> {
}

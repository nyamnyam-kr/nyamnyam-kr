package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.ParticipantEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends MongoRepository<ParticipantEntity, String> {
}

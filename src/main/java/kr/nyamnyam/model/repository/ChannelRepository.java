package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.ChannelEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChannelRepository extends MongoRepository<ChannelEntity, String> {

}

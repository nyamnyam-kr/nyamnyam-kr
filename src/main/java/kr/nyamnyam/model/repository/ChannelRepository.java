package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.ChannelEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface ChannelRepository extends MongoRepository<ChannelEntity, String> {

}

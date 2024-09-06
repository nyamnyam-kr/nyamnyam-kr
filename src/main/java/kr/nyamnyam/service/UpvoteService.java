package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.UpvoteEntity;

import java.util.List;
import java.util.Optional;

public interface UpvoteService {

    List<UpvoteEntity> findAll();

    Optional<UpvoteEntity> findById(Long id);

    UpvoteEntity save(UpvoteEntity entity);

    Boolean deleteById(Long id);

    Boolean existsById(Long id);

    Long count();

}

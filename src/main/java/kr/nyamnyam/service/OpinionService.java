package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.OpinionModel;
import kr.nyamnyam.model.entity.OpinionEntity;

import java.util.List;

public interface OpinionService {

    List<OpinionEntity> findAll();

    OpinionEntity findById(Long id);

    Boolean existsById(Long id);

    Long count();

    OpinionEntity save(OpinionModel model);
}

package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.NoticeModel;
import kr.nyamnyam.model.entity.NoticeEntity;

import java.util.List;

public interface NoticeService {

    List<NoticeEntity> findAll();

    NoticeEntity findById(Long id);

    Boolean existsById(Long id);

 //   Boolean updateHits(Long id);

    Long count();

    Boolean deleteById(Long id);

    NoticeEntity save(NoticeModel model);

    NoticeEntity update(NoticeModel model);

}

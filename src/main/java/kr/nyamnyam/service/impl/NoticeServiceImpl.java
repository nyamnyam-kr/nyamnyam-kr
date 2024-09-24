package kr.nyamnyam.service.impl;

import jakarta.transaction.Transactional;
import kr.nyamnyam.model.domain.NoticeModel;
import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.NoticeEntity;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.repository.NoticeRepository;
import kr.nyamnyam.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public List<NoticeEntity> findAll() {
       return noticeRepository.findAll();
    }

    @Override
    public NoticeEntity findById(Long id) {
        return noticeRepository.findById(id).orElse(null);
    }

//    @Override
//    public NoticeEntity findById(Long id) {
//        updateHits(id);
//        return noticeRepository.findById(id).orElse(null);
//    }


    @Override
    public Boolean existsById(Long id) {
        if (noticeRepository.existsById(id)) {
            noticeRepository.deleteById(id);
        }
        return false;
    }

//    @Override
//    @Transactional
//    public Boolean updateHits(Long id) {
//        noticeRepository.updateHits(id);
//        return true;
//    }

    @Override
    public Long count() {
        return noticeRepository.count();
    }


    @Override
    public Boolean deleteById(Long id) {
        if (noticeRepository.existsById(id)) {
            noticeRepository.deleteById(id);
        }
            return false;
    };

    @Override
    public NoticeEntity save(NoticeModel model) {
        NoticeEntity noticeEntity = NoticeEntity.builder()
                .title(model.getTitle())
                .content(model.getContent())
                .hits(model.getHits())
                .date(LocalDateTime.now())
                .build();

        return noticeRepository.save(noticeEntity);
    }

    @Override
    public NoticeEntity update(NoticeModel model) {
        NoticeEntity noticeEntity = NoticeEntity.builder()
                .id(model.getId())
                .content(model.getContent())
                .title(model.getTitle())
                .hits(model.getHits())
                .date(LocalDateTime.now())
                .build();

        return noticeRepository.save(noticeEntity);
    }



}

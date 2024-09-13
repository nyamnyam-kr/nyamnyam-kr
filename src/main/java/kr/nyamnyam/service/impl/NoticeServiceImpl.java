package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.NoticeModel;
import kr.nyamnyam.model.entity.NoticeEntity;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.repository.NoticeRepository;
import kr.nyamnyam.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


    @Override
    public Boolean existsById(Long id) {
        if (noticeRepository.existsById(id)) {
            noticeRepository.deleteById(id);
        }
        return false;
    }

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
                .hit(model.getHit())
                .date(new Date())
                .build();

        return noticeRepository.save(noticeEntity);
    }

    @Override
    public NoticeEntity update(NoticeModel model) {
        System.out.println("NoticeServiceImpl.update");
        NoticeEntity noticeEntity = NoticeEntity.builder()
                .id(model.getId())
                .title(model.getTitle())
                .content(model.getContent())
                .hit(model.getHit())
                .date(new Date())
                .build();

        return noticeRepository.save(noticeEntity);
    }



}

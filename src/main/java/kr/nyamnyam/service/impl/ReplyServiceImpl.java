package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.ReplyModel;
import kr.nyamnyam.model.entity.ReplyEntity;
import kr.nyamnyam.model.repository.ReplyRepository;
import kr.nyamnyam.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository repository;

    @Override
    public List<ReplyModel> findByPostId(Long postId) {
        List<ReplyEntity> entity = repository.findByPostId(postId);
        return entity.stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReplyModel> findAll() {
        List<ReplyEntity> entity = repository.findAll();
        return entity.stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    @Override
    public ReplyModel findById(Long id) {
        ReplyEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reply not found"));
        return convertToModel(entity);
    }

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(Long id, ReplyModel model) {
        ReplyEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reply not found with id: " + id));

        existingEntity.setContent(model.getContent());
        existingEntity.setModifyDate(LocalDateTime.now());

        repository.save(existingEntity);
        return true;
    }

    @Override
    public Boolean save(ReplyModel model) {
        ReplyEntity entity = convertToEntity(model);
            entity.setEntryDate(LocalDateTime.now());
            repository.save(entity);
        return true;
    }

    private ReplyModel convertToModel(ReplyEntity entity) {
        return ReplyModel.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .postId(entity.getPostId())
                .userId(entity.getUserId())
                .entryDate(entity.getEntryDate())
                .modifyDate(entity.getModifyDate())
                .build();
    }

    public ReplyEntity convertToEntity(ReplyModel model) {
        return ReplyEntity.builder()
                .id(model.getId())
                .content(model.getContent())
                .postId(model.getPostId())
                .userId(model.getUserId())
                .build();
    }
}

package kr.nyamnyam.service.impl;

import com.querydsl.core.Tuple;
import kr.nyamnyam.model.domain.ReplyModel;
import kr.nyamnyam.model.entity.QReplyEntity;
import kr.nyamnyam.model.entity.QUsersEntity;
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
    public List<ReplyModel> findAllByPostId(Long postId) {
        List<Tuple> results = repository.findAllByPostWithNickname(postId);

        results.forEach(tuple -> {
         ReplyEntity replyEntity = tuple.get(QReplyEntity.replyEntity);
         String nickname = tuple.get(QUsersEntity.usersEntity.nickname);
        });

        return results.stream()
                .map(tuple -> {
                    ReplyEntity replyEntity = tuple.get(QReplyEntity.replyEntity);
                    String nickname = tuple.get(QUsersEntity.usersEntity.nickname);
                    return convertToModelWithNickname(replyEntity, nickname);
                })
                .collect(Collectors.toList());
    }

    private ReplyModel convertToModelWithNickname(ReplyEntity replyEntity, String nickname) {
        ReplyModel replyModel = convertToModel(replyEntity);
        replyModel.setNickname(nickname);

        return replyModel;
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
    public ReplyModel update(Long id, ReplyModel model) {
        ReplyEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reply not found with id: " + id));

        existingEntity.setContent(model.getContent());
        existingEntity.setModifyDate(LocalDateTime.now());

        repository.save(existingEntity);
        return model;
    }

    @Override
    public ReplyModel save(ReplyModel model) {
        ReplyEntity entity = convertToEntity(model);
        entity.setEntryDate(LocalDateTime.now());
        repository.save(entity);
        return model;
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

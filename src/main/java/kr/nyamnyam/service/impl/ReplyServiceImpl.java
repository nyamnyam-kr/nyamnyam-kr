package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.ReplyModel;
import kr.nyamnyam.model.entity.ReplyEntity;
import kr.nyamnyam.model.repository.ReplyRepository;
import kr.nyamnyam.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;

    @Override
    public ReplyEntity save(ReplyModel replyModel) {
        ReplyEntity replyEntity = new ReplyEntity();
        return replyRepository.save(replyEntity);
    }

    @Override
    public List<ReplyEntity> findAll() {
        return replyRepository.findAll();
    }

    @Override
    public Optional<ReplyEntity> findById(Long id) {
        return replyRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        replyRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return replyRepository.existsById(id);
    }

    @Override
    public long count() {
        return replyRepository.count();
    }
}

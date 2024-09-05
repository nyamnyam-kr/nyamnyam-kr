package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.entity.ReplyEntity;
import kr.nyamnyam_kr.model.repository.ReplyRepository;
import kr.nyamnyam_kr.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository repository;

    @Override
    public List<?> findAll(Long postId) {
        return repository.findAll();
    }

    @Override
    public ReplyEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Boolean deleteById(Long id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }
}

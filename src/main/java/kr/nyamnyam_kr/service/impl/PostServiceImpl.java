package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.domain.PostModel;
import kr.nyamnyam_kr.model.entity.PostEntity;
import kr.nyamnyam_kr.model.repository.PostRepository;
import kr.nyamnyam_kr.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public PostEntity save(PostModel postModel) {
        PostEntity postEntity = PostEntity.builder()
                .content(postModel.getContent())
                .rating(postModel.getRating())
                .entryDate(postModel.getEntryDate())
                .modifyDate(postModel.getModifyDate())
                .build();
        return postRepository.save(postEntity);
    }

    @Override
    public List<PostEntity> findAll() {
        return postRepository.findAll();
    }

    public List<PostModel> selectAll() {
        List<PostEntity> postEntityList = postRepository.findAll();
        List<PostModel> postModelList = postEntityList.stream()
                .map(postEntity -> PostModel.builder()
                        .content(postEntity.getContent())
                        .rating(postEntity.getRating())
                        .entryDate(postEntity.getEntryDate())
                        .modifyDate(postEntity.getModifyDate())
                        .build())
                .collect(Collectors.toList());
        return postModelList;
    }

    @Override
    public Optional<PostEntity> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return postRepository.existsById(id);
    }

    @Override
    public long count() {
        return postRepository.count();
    }
}

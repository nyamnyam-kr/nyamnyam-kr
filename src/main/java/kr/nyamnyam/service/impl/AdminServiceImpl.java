package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.CountModel;
import kr.nyamnyam.model.repository.PostRepository;
import kr.nyamnyam.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final PostRepository postRepository;


    @Override
    public List<CountModel> countList() {
        return postRepository.findNicknamesWithCounts();
    }

    @Override
    public List<String> postUpvote() {
        return postRepository.postUpvote();
    }

    @Override
    public List<String> findNicknameFromUpvote() {
        return postRepository.findNicknameFromUpvote();
    }

    @Override
    public List<String> findRestaurantFromUpvote() {
        return postRepository.findRestaurantFromUpvote();
    }



}

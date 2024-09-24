package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.Chart.AreaModel;
import kr.nyamnyam.model.domain.Chart.CountModel;
import kr.nyamnyam.model.domain.Chart.TotalModel;
import kr.nyamnyam.model.repository.PostRepository;
import kr.nyamnyam.model.repository.RestaurantRepository;
import kr.nyamnyam.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final PostRepository postRepository;
    private final RestaurantRepository restaurantRepository;


    @Override
    public List<CountModel> countUserList() {
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

    @Override
    public List<AreaModel> countAreaList() {
        return restaurantRepository.countAreaList();
    }

    @Override
    public List<TotalModel> countPostList() {
        return postRepository.countRestaurantList();
    }


}

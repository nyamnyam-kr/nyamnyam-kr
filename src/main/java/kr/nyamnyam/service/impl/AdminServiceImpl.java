package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.Chart.AreaModel;
import kr.nyamnyam.model.domain.Chart.CostModel;
import kr.nyamnyam.model.domain.Chart.CountModel;
import kr.nyamnyam.model.domain.Chart.TotalModel;
import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.model.repository.PostRepository;
import kr.nyamnyam.model.repository.ReceiptRepository;
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
    private final ReceiptRepository receiptRepository;


    //
    @Override
    public List<CountModel> countUserList() {
        return postRepository.findNicknamesWithCounts();
    }


    @Override
    public List<String> postUpvote() {
        return postRepository.postUpvote();
    }

    // 제일 좋아요를 많이 USER
    @Override
    public List<String> findNicknameFromUpvote() {
        return postRepository.findNicknameFromUpvote();
    }

    // 제일 좋아요를 많이 받은 음식점
    @Override
    public List<TotalModel> findRestaurantUpvote() {
        return postRepository.findRestaurantFromUpvote();
    }

    // 레스토랑이 많이 등록된 지역 list
    @Override
    public List<AreaModel> countAreaList() {
        return restaurantRepository.countAreaList();
    }


    // post가 많은 레스토랑
    @Override
    public List<TotalModel> countPostList() {
        return postRepository.countRestaurantList();
    }

    @Override
    public List<TotalModel> recommendByAge(Long userId) {
        return restaurantRepository.restaurantsByAge(userId);
    }

    @Override
    public RestaurantEntity randomRestaurantByUserId(Long userId) {
        return restaurantRepository.randomRestaurant(userId);
    }

    @Override
    public List<CostModel> receiptRestaurant() {
        return receiptRepository.receiptCount();
    }

}

package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.Chart.AreaModel;
import kr.nyamnyam.model.domain.Chart.CostModel;
import kr.nyamnyam.model.domain.Chart.CountModel;
import kr.nyamnyam.model.domain.Chart.TotalModel;
import kr.nyamnyam.model.domain.PostModel;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.model.repository.PostRepository;
import kr.nyamnyam.model.repository.ReceiptRepository;
import kr.nyamnyam.model.repository.RestaurantRepository;
import kr.nyamnyam.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final PostRepository postRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReceiptRepository receiptRepository;
    private final PostServiceImpl postServiceImpl;


    //
    @Override
    public List<CountModel> countUserList() {
        return postRepository.findNicknamesWithCounts();
    }

    // 제일 좋아요를 많이 받은 USER
    @Override
    public List<String> findNicknameFromUpvote() {
        return postRepository.findNicknameFromUpvote();
    }

    // 제일 좋아요를 많이 받은 음식점
    @Override
    public List<TotalModel> findRestaurantFromUpvote() {
        List<TotalModel> restaurantFromUpvote = postRepository.findRestaurantFromUpvote();
        System.out.println(restaurantFromUpvote);


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
    public RestaurantEntity randomRestaurantByUserId(String userId) {
        return restaurantRepository.randomRestaurant(userId);
    }

    @Override
    public List<CostModel> receiptRestaurant() {
        return receiptRepository.receiptCount();
    }

    @Override
    public List<CountModel> typeList(String userId) {
        return postRepository.typeList(userId);
    }

    @Override
    public List<AreaModel> userAreaList(String id) {
        return restaurantRepository.userAreaList(id);
    }

    @Override
    public List<PostModel> findPostsByToday() {
        List<PostEntity> postsByToday = postRepository.findPostsByToday();

        return postsByToday.stream()
                .map(postServiceImpl::convertToModel)
                .collect(Collectors.toList());
    }

//    @Override
//    public Boolean postUpdateDisabled(Long postId) {
//        PostEntity post = postRepository.findById(postId).orElse(null);
//        if (!post.getEnabled().equals(true)) {
//            return false;
//        } else {
//            post.setEnabled(false);
//            return true;
//        }
//    }
//
//    @Override
//    public Boolean postAble(Long postId) {
//        return null;
//    }


}

package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.Chart.AreaModel;
import kr.nyamnyam.model.domain.Chart.CostModel;
import kr.nyamnyam.model.domain.Chart.CountModel;
import kr.nyamnyam.model.domain.Chart.TotalModel;
import kr.nyamnyam.model.entity.RestaurantEntity;

import java.util.List;

public interface AdminService {

    List<CountModel> countUserList();

    List<String> findNicknameFromUpvote();

    List<TotalModel> findRestaurantFromUpvote();

    List<AreaModel> countAreaList();

    List<TotalModel> countPostList();

   // List<TotalModel> recommendByAge(String userId);

    RestaurantEntity randomRestaurantByUserId(String userId);

    List<CostModel> receiptRestaurant();

}

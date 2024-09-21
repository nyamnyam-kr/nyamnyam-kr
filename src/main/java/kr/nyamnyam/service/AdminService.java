package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.Chart.AreaModel;
import kr.nyamnyam.model.domain.Chart.CountModel;
import kr.nyamnyam.model.domain.Chart.TotalModel;

import java.util.List;

public interface AdminService {

    List<CountModel> countUserList();

    List<String> postUpvote();

    List<String> findNicknameFromUpvote();

    List<String> findRestaurantFromUpvote();

    List<AreaModel> countAreaList();

    List<TotalModel> countPostList();
}

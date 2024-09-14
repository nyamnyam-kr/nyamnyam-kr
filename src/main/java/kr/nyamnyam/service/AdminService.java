package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.CountModel;

import java.util.List;

public interface AdminService {

    List<CountModel> countList();

    List<String> postUpvote();

    List<String> findNicknameFromUpvote();

    List<String> findRestaurantFromUpvote();


}

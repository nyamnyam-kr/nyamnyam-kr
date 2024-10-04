package kr.nyamnyam.model.repository.Custom;

import com.querydsl.core.Tuple;

import kr.nyamnyam.model.domain.Chart.CountModel;
import kr.nyamnyam.model.domain.Chart.TotalModel;
import kr.nyamnyam.model.domain.Chart.UserPostModel;

import java.util.List;

public interface PostRepositoryCustom {

    List<Tuple> findAllByRestaurantWithNickname(Long restaurantId);

    // 단일 post에 nickname 불러오기
    Tuple findPostWithNicknameById(Long postId);

    List<CountModel> findNicknamesWithCounts();

    List<String> postUpvote();

    List<String> findNicknameFromUpvote();

    List<String> findRestaurantFromUpvote();

    List<TotalModel> countRestaurantList();

    List<UserPostModel> findByUserId(Long userId);


}

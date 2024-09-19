package kr.nyamnyam.model.repository.Custom;

import kr.nyamnyam.model.domain.CountModel;

import java.util.List;

public interface PostRepositoryCustom {

    List<CountModel> findNicknamesWithCounts();

    List<String> postUpvote();

    List<String> findNicknameFromUpvote();

    List<String> findRestaurantFromUpvote();
}

package kr.nyamnyam.model.repository.Custom;

import kr.nyamnyam.model.domain.Chart.CountModel;
import kr.nyamnyam.model.domain.Chart.TotalModel;

import java.util.List;

public interface PostRepositoryCustom {

    List<CountModel> findNicknamesWithCounts();

    List<String> postUpvote();

    List<String> findNicknameFromUpvote();

    List<String> findRestaurantFromUpvote();

    List<TotalModel> countRestaurantList();

}

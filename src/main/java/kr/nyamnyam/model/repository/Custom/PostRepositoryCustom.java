package kr.nyamnyam.model.repository.Custom;

import com.querydsl.core.Tuple;
import kr.nyamnyam.model.domain.CountModel;
import kr.nyamnyam.model.domain.PostModel;
import kr.nyamnyam.model.entity.PostEntity;

import java.util.List;

public interface PostRepositoryCustom {

    List<Tuple> findAllByRestaurantWithNickname(Long restaurantId);

    List<CountModel> findNicknamesWithCounts();

    List<String> postUpvote();

    List<String> findNicknameFromUpvote();

    List<String> findRestaurantFromUpvote();
}

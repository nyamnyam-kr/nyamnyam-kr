package kr.nyamnyam.model.repository.Custom;

import kr.nyamnyam.model.entity.PostEntity;

import java.util.List;

public interface PostRepositoryCustom {

    List<String> postUpvote();

    List<String> findNicknameFromUpvote();

    List<String> findRestaurantFromUpvote();
}

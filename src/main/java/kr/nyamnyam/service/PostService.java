package kr.nyamnyam.service;


import kr.nyamnyam.model.domain.PostModel;
import kr.nyamnyam.model.entity.PostEntity;

import java.util.List;

public interface PostService {

    double allAverageRating(Long restaurantId);

    PostModel postWithImage(Long id);

    PostEntity findEntityById(Long id);

    List<PostModel> findAllByRestaurant(Long restaurantId);

    PostModel findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);

    List<PostModel> findAllPerPage(int page);

    Boolean crawling();

    Long createPost(PostModel model);

    Boolean updatePost(Long id, PostModel model);
}


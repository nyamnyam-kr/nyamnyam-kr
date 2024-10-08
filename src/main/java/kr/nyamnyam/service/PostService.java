package kr.nyamnyam.service;


import kr.nyamnyam.model.domain.Chart.UserPostModel;
import kr.nyamnyam.model.domain.PostModel;
import kr.nyamnyam.model.entity.PostEntity;
import org.springframework.web.multipart.MultipartFile;

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

    Long updatePost(PostModel model);

    List<UserPostModel> findByUserId(String userId);

    Long createPostWithImages(PostModel model);
}


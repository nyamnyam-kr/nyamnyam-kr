package kr.nyamnyam.service;


import kr.nyamnyam.model.domain.PostModel;

import java.util.List;

public interface PostService {

    List<PostModel> findAll();

    PostModel findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);

    Boolean save(PostModel model);

    List<PostModel> findAllPerPage(int page);

    Boolean crawling();
}


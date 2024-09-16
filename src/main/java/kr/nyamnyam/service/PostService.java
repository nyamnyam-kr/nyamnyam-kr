package kr.nyamnyam.service;


import kr.nyamnyam.model.entity.PostEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {

    List<PostEntity> findAll();

    PostEntity findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);

    Boolean save(PostEntity entity);

    List<PostEntity> findAllPerPage(int page);

    Boolean crawling();
}


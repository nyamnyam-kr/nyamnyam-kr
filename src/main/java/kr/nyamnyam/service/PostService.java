package kr.nyamnyam.service;


import kr.nyamnyam.model.entity.PostEntity;
import java.util.List;

public interface PostService {

    List<PostEntity> findAll();

    PostEntity findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);

    PostEntity save(PostEntity entity);

}


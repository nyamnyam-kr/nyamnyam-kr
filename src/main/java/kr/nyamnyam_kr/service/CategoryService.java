package kr.nyamnyam_kr.service;

import kr.nyamnyam_kr.model.domain.CategoryModel;
import kr.nyamnyam_kr.model.entity.CategoryEntity;


import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryEntity save(CategoryModel categoryModel);

    List<CategoryEntity> findAll();

    Optional<CategoryEntity> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}

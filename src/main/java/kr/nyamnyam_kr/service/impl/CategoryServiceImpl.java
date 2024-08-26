package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.domain.CategoryModel;
import kr.nyamnyam_kr.model.entity.CategoryEntity;
import kr.nyamnyam_kr.model.repository.CategoryRepository;
import kr.nyamnyam_kr.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryEntity save(CategoryModel categoryModel) {
        CategoryEntity categoryEntity = new CategoryEntity();
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<CategoryEntity> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }
}

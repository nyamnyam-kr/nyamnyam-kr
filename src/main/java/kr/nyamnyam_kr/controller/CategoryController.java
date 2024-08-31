package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.domain.CategoryModel;
import kr.nyamnyam_kr.model.entity.CategoryEntity;
import kr.nyamnyam_kr.model.repository.CategoryRepository;
import kr.nyamnyam_kr.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category/")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @PostMapping("save")
    public CategoryEntity save(@RequestBody CategoryEntity entity) {

        return categoryRepository.save(entity.builder()
                        .name(entity.getName())
                .build());
    }

    @GetMapping("findAll")
    public List<CategoryEntity> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("findById")
    public Optional<CategoryEntity> findById(Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("deleteById")
    public void deleteById(Long id) {
        categoryService.deleteById(id);
    }

    @GetMapping("existsById")
    public boolean existsById(Long id) {
        return categoryService.existsById(id);
    }

    @GetMapping("count")
    public long count() {
        return categoryService.count();
    }
}

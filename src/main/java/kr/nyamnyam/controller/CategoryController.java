package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.CategoryModel;
import kr.nyamnyam.model.entity.CategoryEntity;
import kr.nyamnyam.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category/")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("save")
    public CategoryEntity save(CategoryModel categoryModel) {
        return categoryService.save(categoryModel);
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

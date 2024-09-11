package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.CategoryModel;
import kr.nyamnyam.model.entity.CategoryEntity;
import kr.nyamnyam.model.repository.CategoryRepository;
import kr.nyamnyam.service.CategoryService;
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


    @Override
    public String extractCategory(String menu) {
        if (menu.contains("짜장") || menu.contains("짬뽕") || menu.contains("탕수육")
                || menu.contains("중국")) {
            return "중식";

        } else if (menu.contains("라멘") || menu.contains("스시") || menu.contains("우동")
                || menu.contains("일본")) {
            return "일식";

        } else if (menu.contains("김치") || menu.contains("비빔밥") || menu.contains("불고기")
                || menu.contains("샤브") || menu.contains("국수") || menu.contains("만두") || menu.contains("냉면")
                || menu.contains("녹두") || menu.contains("게장") || menu.contains("낙지")
                || menu.contains("곰탕")|| menu.contains("갈비") || menu.contains("삼겹살")) {
            return "한식";

        } else if (menu.contains("떡볶이") || menu.contains("순대") || menu.contains("튀김")) {
            return "분식";

        } else if (menu.contains("돈까스") || menu.contains("스테이크") || menu.contains("햄버거")) {
            return "경양식";

        } else if (menu.contains("파스타") || menu.contains("피자") || menu.contains("리조또")
        || menu.contains("리코타")) {
            return "양식";

        } else if (menu.contains("아메리카노") || menu.contains("카푸치노") || menu.contains("라떼")
                || menu.contains("베이커리")) {
            return "카페";

        } else if (menu.contains("케이크") || menu.contains("빙수") || menu.contains("마카롱")
                || menu.contains("베이커리")) {
            return "디저트";
        }

        return "기타";
    }
}

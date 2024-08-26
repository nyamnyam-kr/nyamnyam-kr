package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}

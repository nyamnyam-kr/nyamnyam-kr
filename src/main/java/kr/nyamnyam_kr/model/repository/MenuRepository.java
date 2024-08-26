package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity,Long> {
}

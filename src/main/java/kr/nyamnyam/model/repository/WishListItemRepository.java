package kr.nyamnyam.model.repository;

import jakarta.transaction.Transactional;
import kr.nyamnyam.model.entity.WishListEntity;
import kr.nyamnyam.model.entity.WishListItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface WishListItemRepository extends JpaRepository<WishListItemEntity, Long> {
}

package kr.nyamnyam.model.repository;

import jakarta.transaction.Transactional;
import kr.nyamnyam.model.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface WishListRepository extends JpaRepository<WishListEntity, Long> {
}

package kr.nyamnyam.model.repository;


import kr.nyamnyam.model.entity.WishListEntity;
import kr.nyamnyam.model.repository.Custom.WishListCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WishListRepository extends JpaRepository<WishListEntity, Long>, WishListCustomRepository {

    boolean existsByName(String name);

    boolean existsByIdAndUserId(Long wishListId, Long userId);
}

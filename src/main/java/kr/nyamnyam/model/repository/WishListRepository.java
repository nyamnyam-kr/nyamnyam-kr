package kr.nyamnyam.model.repository;


import kr.nyamnyam.model.entity.WishListEntity;
import kr.nyamnyam.model.repository.Custom.WishListCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface WishListRepository extends JpaRepository<WishListEntity, String>, WishListCustomRepository {

    boolean existsByName(String name);

    boolean existsByIdAndUserId(Long wishListId, String userId);

    boolean existsByNameAndUserId(String name, String userId);



}

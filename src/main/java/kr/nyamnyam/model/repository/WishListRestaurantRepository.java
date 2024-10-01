package kr.nyamnyam.model.repository;



import kr.nyamnyam.model.entity.WishListRestaurantEntity;
import kr.nyamnyam.model.repository.Custom.WishListRestaurantCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRestaurantRepository extends JpaRepository<WishListRestaurantEntity, Long> , WishListRestaurantCustomRepository {

    boolean existsByWishListIdAndRestaurantId(Long wishListId, Long restaurantId);


}

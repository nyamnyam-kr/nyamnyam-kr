package kr.nyamnyam.model.repository;



import kr.nyamnyam.model.entity.WishListRestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRestaurantRepository extends JpaRepository<WishListRestaurantEntity, Long> {

    boolean existsByWishListIdAndRestaurantId(Long wishListId, Long restaurantId);
}

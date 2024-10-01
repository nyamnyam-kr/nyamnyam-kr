package kr.nyamnyam.model.repository.Custom;

import kr.nyamnyam.model.entity.RestaurantEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WishListRestaurantCustomRepository {

    List<RestaurantEntity> findRestaurantsByUserIdAndWishListId(Long userId, Long wishListId);


    @Transactional
    boolean deleteRestaurantFromWishList(Long userId, Long restaurantId);

    List<Long> getDistinctRestaurantIdsByUserId(Long userId);
}

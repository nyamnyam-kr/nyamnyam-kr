package kr.nyamnyam.model.repository.Custom;

import kr.nyamnyam.model.entity.RestaurantEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WishListRestaurantCustomRepository {

    List<RestaurantEntity> findRestaurantsByUserIdAndWishListId(String userId, Long wishListId);


    @Transactional
    boolean deleteRestaurantFromWishList(String userId, Long restaurantId);

    List<Long> getDistinctRestaurantIdsByUserId(String userId);
}

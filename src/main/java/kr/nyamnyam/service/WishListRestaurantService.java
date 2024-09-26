package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.WishListRestaurantEntity;

import java.util.List;

public interface WishListRestaurantService {

    WishListRestaurantEntity addRestaurantToWishList(Long userId, Long wishListId, Long restaurantId);

    List<RestaurantModel> findRestaurantsByUserIdAndWishListId(Long userId, Long wishListId);
}

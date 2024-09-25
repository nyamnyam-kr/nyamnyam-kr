package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.WishListRestaurantEntity;

public interface WishListRestaurantService {

    WishListRestaurantEntity addRestaurantToWishList(Long userId, Long wishListId, Long restaurantId);
}

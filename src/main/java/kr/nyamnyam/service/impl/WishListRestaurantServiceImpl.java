package kr.nyamnyam.service.impl;


import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.model.entity.WishListRestaurantEntity;
import kr.nyamnyam.model.repository.RestaurantRepository;
import kr.nyamnyam.model.repository.WishListRepository;
import kr.nyamnyam.model.repository.WishListRestaurantRepository;
import kr.nyamnyam.service.WishListRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListRestaurantServiceImpl implements WishListRestaurantService {
    private final WishListRestaurantRepository wishListRestaurantRepository;
    private final WishListRepository wishListRepository;
    private final RestaurantRepository restaurantRepository;


    @Override
    public WishListRestaurantEntity addRestaurantToWishList(Long userId, Long wishListId, Long restaurantId) {


        if (!wishListRepository.existsByIdAndUserId(wishListId, userId)) {
            return null;
        }
        if (wishListRestaurantRepository.existsByWishListIdAndRestaurantId(wishListId, restaurantId)) {
            return null;
        }

        WishListRestaurantEntity wishListRestaurant = WishListRestaurantEntity.builder()
                .wishListId(wishListId)
                .restaurantId(restaurantId)
                .userId(userId)
                .build();


        return wishListRestaurantRepository.save(wishListRestaurant);
    }

    @Override
    public List<RestaurantModel> findRestaurantsByUserIdAndWishListId(Long userId, Long wishListId) {
        List<RestaurantEntity> restaurants = wishListRestaurantRepository.findRestaurantsByUserIdAndWishListId(userId, wishListId);
        return restaurants.stream()
                .map(RestaurantModel::toDto)
                .collect(Collectors.toList());
    }

}

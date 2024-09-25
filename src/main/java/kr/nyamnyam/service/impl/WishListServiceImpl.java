package kr.nyamnyam.service.impl;


import kr.nyamnyam.model.entity.WishListEntity;
import kr.nyamnyam.model.repository.RestaurantRepository;
import kr.nyamnyam.model.repository.WishListRepository;
import kr.nyamnyam.model.repository.WishListRestaurantRepository;
import kr.nyamnyam.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {
    private final WishListRepository wishListRepository;


    @Override
    public WishListEntity createWishList(Long userId, String name) {
        if (wishListRepository.existsByName(name)) {
            return null;
        }

        WishListEntity wishList = WishListEntity.builder()
                .name(name)
                .userId(userId)
                .build();
        return wishListRepository.save(wishList);
    }





}

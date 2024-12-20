package kr.nyamnyam.service.impl;


import kr.nyamnyam.model.domain.WishListModel;
import kr.nyamnyam.model.entity.WishListEntity;
import kr.nyamnyam.model.repository.WishListRepository;
import kr.nyamnyam.model.repository.WishListRestaurantRepository;
import kr.nyamnyam.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {
    private final WishListRepository wishListRepository;


    @Override
    public WishListEntity createWishList(String name, String userId) {
        if (wishListRepository.existsByNameAndUserId(name, userId)) {
            return null;
        }

        WishListEntity wishList = WishListEntity.builder()
                .name(name)
                .userId(userId)
                .build();
        return wishListRepository.save(wishList);
    }


    @Override
    public List<WishListModel> getWishLists(String userId) {
        List<WishListEntity> wishLists = wishListRepository.getWishLists(userId);
        return wishLists.stream()
                .map(WishListModel::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean deleteWishList(String userId, Long wishListId) {
        boolean isDeleted = wishListRepository.deleteWishList(userId, wishListId);
        return isDeleted;
    }





}

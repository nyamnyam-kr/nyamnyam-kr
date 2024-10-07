package kr.nyamnyam.service;


import kr.nyamnyam.model.domain.WishListModel;
import kr.nyamnyam.model.entity.WishListEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface WishListService {


    WishListEntity createWishList(String name, String userId);

    List<WishListModel> getWishLists(String userId);


    @Transactional
    boolean deleteWishList(String userId, Long wishListId);
}

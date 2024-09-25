package kr.nyamnyam.service;


import kr.nyamnyam.model.entity.WishListEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface WishListService {


    WishListEntity createWishList(Long userId, String name);
}

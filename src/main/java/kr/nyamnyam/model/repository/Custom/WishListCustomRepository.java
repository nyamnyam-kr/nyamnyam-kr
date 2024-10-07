package kr.nyamnyam.model.repository.Custom;

import kr.nyamnyam.model.entity.WishListEntity;

import java.util.List;

public interface WishListCustomRepository {

    List<WishListEntity> getWishLists(String userId);

    boolean deleteWishList(String userId, Long id);
}

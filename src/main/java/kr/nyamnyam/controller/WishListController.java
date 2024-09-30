package kr.nyamnyam.controller;


import jakarta.websocket.server.PathParam;
import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.domain.WishListModel;
import kr.nyamnyam.model.entity.WishListEntity;
import kr.nyamnyam.model.entity.WishListRestaurantEntity;
import kr.nyamnyam.service.WishListRestaurantService;
import kr.nyamnyam.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/wishList")
public class WishListController {
    private final WishListService wishListService;
    private final WishListRestaurantService wishListRestaurantService;

    @PostMapping
    public ResponseEntity<WishListEntity> createWishList(@RequestHeader Long userId, @RequestParam String name) {
        WishListEntity wishList = wishListService.createWishList(name, userId);
        return ResponseEntity.ok(wishList);
    }

    @PostMapping("/{wishListId}")
    public ResponseEntity<WishListRestaurantEntity> addRestaurantToWishList(@RequestHeader Long userId, @PathVariable Long wishListId, @RequestParam Long restaurantId) {
        WishListRestaurantEntity wishListRestaurantEntity = wishListRestaurantService.addRestaurantToWishList(userId, wishListId, restaurantId);
        return ResponseEntity.ok(wishListRestaurantEntity);
    }

    @GetMapping
    public List<WishListModel> getWishLists(@RequestHeader Long userId) {
        return wishListService.getWishLists(userId);
    }

/*    @GetMapping("/{wishListId}/restaurants")
    public ResponseEntity<List<RestaurantModel>> getRestaurants(
            @RequestHeader Long userId,
            @PathVariable Long wishListId) {
        List<RestaurantModel> restaurants = wishListRestaurantService.findRestaurantsByUserIdAndWishListId(userId, wishListId);
        return ResponseEntity.ok(restaurants);
    }*/

    @GetMapping("/{wishListName}/restaurants")
    public ResponseEntity<List<RestaurantModel>> getRestaurants(
            @RequestHeader Long userId,
            @RequestParam Long wishListId, @PathVariable String wishListName) {
        List<RestaurantModel> restaurants = wishListRestaurantService.findRestaurantsByUserIdAndWishListId(userId, wishListId);
        return ResponseEntity.ok(restaurants);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteWishList(@RequestHeader Long userId , @RequestParam Long restaurantId) {
        boolean deleted = wishListRestaurantService.deleteRestaurantFromWishList(userId, restaurantId);
        return ResponseEntity.ok(deleted);
    }


}

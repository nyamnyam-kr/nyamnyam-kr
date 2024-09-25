package kr.nyamnyam.controller;


import jakarta.websocket.server.PathParam;
import kr.nyamnyam.model.entity.WishListEntity;
import kr.nyamnyam.model.entity.WishListRestaurantEntity;
import kr.nyamnyam.service.WishListRestaurantService;
import kr.nyamnyam.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/wishList")
public class WishListController {
    private final WishListService wishListService;
    private final WishListRestaurantService wishListRestaurantService;

    @PostMapping
    public ResponseEntity<WishListEntity> createWishList(@RequestHeader Long userId, @RequestParam String name) {
        WishListEntity wishList = wishListService.createWishList(userId, name);
        return ResponseEntity.ok(wishList);
    }

    @PostMapping("/{wishListName}")
    public ResponseEntity<WishListRestaurantEntity> addRestaurantToWishList(@RequestHeader Long userId, @RequestParam Long wishListId, @RequestParam Long restaurantId, @PathVariable String wishListName) {
        WishListRestaurantEntity wishListRestaurantEntity = wishListRestaurantService.addRestaurantToWishList(userId, wishListId, restaurantId);
        return ResponseEntity.ok(wishListRestaurantEntity);
    }
}

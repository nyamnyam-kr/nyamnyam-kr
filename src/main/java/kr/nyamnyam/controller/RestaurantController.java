package kr.nyamnyam.controller;

import kr.nyamnyam.service.ApiService;
import kr.nyamnyam.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {
    private final ApiService apiService;
    private final RestaurantService restaurantService;




    @GetMapping("/api")
    public boolean getRestaurants() {
        return apiService.getRestaurants();
    }

   /* @GetMapping("/showPosts")
    public
*/

}

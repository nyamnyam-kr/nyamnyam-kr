package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.domain.ReplyModel;
import kr.nyamnyam_kr.model.domain.RestaurantModel;
import kr.nyamnyam_kr.model.entity.ReplyEntity;
import kr.nyamnyam_kr.model.entity.RestaurantEntity;
import kr.nyamnyam_kr.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;


    @GetMapping("/save")
    public String save() {
        return "restaurants/saveRestaurant";
    }

    @PutMapping("/save/{id}")
    public String save(RestaurantModel restaurantModel, @PathVariable long id) {
        RestaurantEntity save = restaurantService.save(restaurantModel);
        save.setId(id);
        return "redirect:/restaurant/findOne/{id}";
    }

    @GetMapping("/findAll")
    public String findAll(Model model) {
        List<RestaurantEntity> all = restaurantService.findAll();
        model.addAttribute("list", all);
        return "restaurants/showRestaurants";
    }

    @GetMapping("/findOne/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Optional<RestaurantEntity> restaurantOpt = restaurantService.findById(id);
            model.addAttribute("restaurant", restaurantOpt.get());
        return "restaurants/restaurantOne";
    }

    @GetMapping("deleteById")
    public void deleteById(Long id) {
        restaurantService.deleteById(id);
    }

    @GetMapping("/existsById")
    public boolean existsById(Long id) {
        return restaurantService.existsById(id);
    }

    @GetMapping("/count")
    public long count() {
        return restaurantService.count();
    }

    @RequestMapping("/")
    public String hello(Model model) {
        model.addAttribute("hello", "dfgdfg");
        return "/index";
    }


}

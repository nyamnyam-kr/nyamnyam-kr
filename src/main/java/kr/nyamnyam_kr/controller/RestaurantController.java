package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.domain.ReplyModel;
import kr.nyamnyam_kr.model.domain.RestaurantModel;
import kr.nyamnyam_kr.model.entity.ReplyEntity;
import kr.nyamnyam_kr.model.entity.RestaurantEntity;
import kr.nyamnyam_kr.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/save")
    public RestaurantEntity save(RestaurantModel restaurantModel) {
        return restaurantService.save(restaurantModel);
    }

    @GetMapping("/findAll")
    public List<RestaurantEntity> findAll() {
        return restaurantService.findAll();
    }

    @GetMapping("/findById")
    public Optional<RestaurantEntity> findById(Long id) {
        return restaurantService.findById(id);
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

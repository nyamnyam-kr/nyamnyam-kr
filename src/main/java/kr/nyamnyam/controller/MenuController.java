package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.MenuModel;
import kr.nyamnyam.model.entity.MenuEntity;
import kr.nyamnyam.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/menu/")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("save")
    public MenuEntity save(MenuModel menuModel) {
        return menuService.save(menuModel);
    }

    @GetMapping("findAll")
    public List<MenuEntity> findAll() {
        return menuService.findAll();
    }

    @GetMapping("findById")
    public Optional<MenuEntity> findById(Long id) {
        return menuService.findById(id);
    }

    @GetMapping("deleteById")
    public void deleteById(Long id) {
        menuService.deleteById(id);
    }

    @GetMapping("existsById")
    public boolean existsById(Long id) {
        return menuService.existsById(id);
    }

    @GetMapping("count")
    public long count() {
        return menuService.count();
    }
}

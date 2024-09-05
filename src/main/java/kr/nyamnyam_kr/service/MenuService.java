package kr.nyamnyam_kr.service;


import kr.nyamnyam_kr.model.domain.MenuModel;
import kr.nyamnyam_kr.model.entity.MenuEntity;

import java.util.List;
import java.util.Optional;

public interface MenuService {
    MenuEntity save(MenuModel menuModel);

    List<MenuEntity> findAll();

    Optional<MenuEntity> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}

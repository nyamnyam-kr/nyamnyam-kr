package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.MenuModel;
import kr.nyamnyam.model.entity.MenuEntity;
import kr.nyamnyam.model.repository.MenuRepository;
import kr.nyamnyam.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    @Override
    public MenuEntity save(MenuModel menuModel) {
        MenuEntity menuEntity = new MenuEntity();
        return menuRepository.save(menuEntity);
    }

    @Override
    public List<MenuEntity> findAll() {
        return menuRepository.findAll();
    }

    @Override
    public Optional<MenuEntity> findById(Long id) {
        return menuRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        menuRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return menuRepository.existsById(id);
    }

    @Override
    public long count() {
        return menuRepository.count();
    }
}

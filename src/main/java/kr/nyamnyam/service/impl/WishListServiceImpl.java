package kr.nyamnyam.service.impl;


import kr.nyamnyam.model.repository.WishListGroupRepository;
import kr.nyamnyam.model.repository.WishListItemRepository;
import kr.nyamnyam.model.repository.WishListRepository;
import kr.nyamnyam.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {
    private final WishListRepository wishListRepository;
    private final WishListGroupRepository wishListGroupRepository;
    private final WishListItemRepository wishListItemRepository;



}

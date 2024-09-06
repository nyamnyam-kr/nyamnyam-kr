package kr.nyamnyam_kr.model.domain;

import jakarta.persistence.*;
import kr.nyamnyam_kr.model.entity.MenuEntity;
import kr.nyamnyam_kr.model.entity.RestaurantEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class CategoryModel {
    private Long id;
    private String name;
    private Long menuId;

}

package kr.nyamnyam.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "wishlist_group")
public class WishListGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String wishlistId;
    private String groupName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

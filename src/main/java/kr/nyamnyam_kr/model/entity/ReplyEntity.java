package kr.nyamnyam_kr.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="reply")
public class ReplyEntity {
    @Id
    @GeneratedValue()
    @Column(name="id")
    private Long id;

    @Column(name="content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurantEntity;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;
}

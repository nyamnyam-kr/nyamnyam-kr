package kr.nyamnyam.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tags")
public class TagEntity {
    @Id
    private String name;

    @Enumerated(EnumType.STRING)
    private TagCategory tagCategory;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<PostTagEntity> postTag = new ArrayList<>();
}

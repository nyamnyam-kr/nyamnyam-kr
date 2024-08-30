package kr.nyamnyam_kr.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@ToString
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)//Args 는 이거냐 레이지냐 => 얘는 생성자가 실행될때 작동한다. 상속할때 부모꺼 까지 가능
@AllArgsConstructor
public class CategoryEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @OneToOne(mappedBy = "categoryEntity")
    private MenuEntity menuEntity;
}

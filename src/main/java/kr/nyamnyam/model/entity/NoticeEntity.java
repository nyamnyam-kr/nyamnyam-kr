package kr.nyamnyam.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "notice")
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Long hits;
    private Date date;



}

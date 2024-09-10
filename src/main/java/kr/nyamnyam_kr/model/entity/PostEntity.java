package kr.nyamnyam_kr.model.entity;

import jakarta.persistence.*;
import kr.nyamnyam_kr.model.domain.PostModel;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Long taste;
    private Long clean;
    private Long service;
    private Date entryDate;
    private Date modifyDate;

    public static PostEntity toPostEntity(PostModel postModel) {
        PostEntity postEntity = new PostEntity();
        postEntity.setContent(postModel.getContent());
        postEntity.setTaste(postModel.getTaste());
        postEntity.setClean(postModel.getClean());
        postEntity.setService(postModel.getService());
        postEntity.setEntryDate(postModel.getEntryDate());
        postEntity.setModifyDate(postModel.getModifyDate());
        return postEntity;
    }

}
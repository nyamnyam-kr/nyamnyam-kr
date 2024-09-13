package kr.nyamnyam.model.entity;

import jakarta.persistence.*;

import java.nio.file.Path;

@Entity
@Table(name = "postTags")
public class PostTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_name", referencedColumnName = "name", nullable = false)
    private TagEntity tag;

    public TagEntity getTag() {
        return tag;
    }
}

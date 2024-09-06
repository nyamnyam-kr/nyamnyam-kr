package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

}

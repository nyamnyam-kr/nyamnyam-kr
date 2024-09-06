package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

}

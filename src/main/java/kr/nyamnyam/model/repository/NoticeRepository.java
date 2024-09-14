package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.NoticeEntity;
import kr.nyamnyam.model.repository.Custom.NoticeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long>, NoticeRepositoryCustom {

}

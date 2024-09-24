package kr.nyamnyam.model.repository;

import jakarta.transaction.Transactional;
import kr.nyamnyam.model.entity.NoticeEntity;
import kr.nyamnyam.model.repository.Custom.NoticeRepositoryCustom;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long>, NoticeRepositoryCustom {



}

package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.ChannelEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChannelService {

    ChannelEntity save(ChannelEntity channelEntity);

    List<ChannelEntity> findAll();

    List<ChannelEntity> findAllPerPage(int page);

    Optional<ChannelEntity> findById(String id);

    boolean deleteById(String id);

    boolean existsById(String id);

    long count();


    List<ChannelEntity> crawling();
}

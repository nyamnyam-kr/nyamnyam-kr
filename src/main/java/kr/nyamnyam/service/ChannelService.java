package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.ChannelEntity;

import java.util.List;
import java.util.Optional;

public interface ChannelService {

    ChannelEntity save(ChannelEntity channelEntity);

    List<ChannelEntity> findAll();

    Optional<ChannelEntity> findById(String id);

    boolean deleteById(String id);

    boolean existsById(String id);

    long count();
}

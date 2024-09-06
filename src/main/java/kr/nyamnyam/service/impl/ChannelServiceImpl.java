package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.ChannelModel;
import kr.nyamnyam.model.entity.ChannelEntity;
import kr.nyamnyam.model.repository.ChannelRepository;
import kr.nyamnyam.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;


    @Override
    public ChannelEntity save(ChannelEntity channelEntity) {

        return channelRepository.save(channelEntity);
    }

    @Override
    public List<ChannelEntity> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public Optional<ChannelEntity> findById(String id) {
        return channelRepository.findById(id);
    }

    @Override
    public boolean deleteById(String id) {
        if (channelRepository.existsById(id)) {
            channelRepository.deleteById(id);
            return true;
        }
        return false;

    }

    @Override
    public boolean existsById(String id) {
        return channelRepository.existsById(id);
    }

    @Override
    public long count() {
        return channelRepository.count();
    }
}

package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.domain.GroupReplyModel;
import kr.nyamnyam_kr.model.entity.GroupReplyEntity;
import kr.nyamnyam_kr.model.repository.GroupReplyRepository;
import kr.nyamnyam_kr.service.GroupReplyService;
import kr.nyamnyam_kr.service.GroupService;
import kr.nyamnyam_kr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GroupReplyServiceImpl implements GroupReplyService {
    private final GroupReplyRepository groupReplyRepository;
    private final GroupService groupService;
    private final UserService userService;

    @Override
    public GroupReplyEntity save(Long userId, GroupReplyModel groupReplyModel,Long groupId) {
        GroupReplyEntity groupReplyEntity=GroupReplyEntity.builder()
                .content(groupReplyModel.getContent())
                .user(userService.findById(userId).get())
                .group(groupService.findById(groupId).get())
                .build();
        return groupReplyRepository.save(groupReplyEntity);
    }

    @Override
    public List<GroupReplyEntity> findAll(Long groupId) {

        return groupReplyRepository.findAllByGroupId(groupId);
    }

    @Override
    public Optional<GroupReplyEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }
}

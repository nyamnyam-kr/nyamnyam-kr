package kr.nyamnyam_kr.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam_kr.model.domain.GroupModel;
import kr.nyamnyam_kr.model.entity.GroupEntity;
import kr.nyamnyam_kr.model.repository.GroupRepository;
import kr.nyamnyam_kr.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private JPAQueryFactory queryFactory;

    @Override
    public Boolean save(GroupModel groupModel) {
        GroupEntity groupEntity = GroupEntity.builder()
                .name(groupModel.getName())
                .content(groupModel.getContent())
                .dDay(groupModel.getDDay())
                .people(groupModel.getPeople())
                .build();
        System.out.println("Converted GroupEntity: " + groupEntity);
        GroupEntity savedGroupEntity = groupRepository.save(groupEntity);
        return savedGroupEntity != null && savedGroupEntity.getId() != null;
    }

    @Override
    public Boolean update(GroupModel groupModel) {
        GroupEntity groupEntity = GroupEntity.builder()
                .name(groupModel.getName())
                .content(groupModel.getContent())
                .entryDate(groupModel.getEntryDate())
                .dDay(groupModel.getDDay())
                .people(groupModel.getPeople())
                .build();
        GroupEntity savedGroupEntity = groupRepository.save(groupEntity);
        return savedGroupEntity != null && savedGroupEntity.getId() != null;
    }

    @Override
    public List<GroupEntity> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Optional<GroupEntity> findById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return groupRepository.existsById(id);
    }

    @Override
    public long count() {
        return groupRepository.count();
    }
}

package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.domain.GroupModel;
import kr.nyamnyam_kr.model.entity.GroupEntity;
import kr.nyamnyam_kr.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/save")
    public GroupEntity save(GroupModel groupModel) {
        return groupService.save(groupModel);
    }

    @PutMapping("update/{id}")
    public GroupEntity update(GroupModel groupModel, @PathVariable Long id) {
        return groupService.save(groupModel);
    }

    @GetMapping("/findAll")
    public List<GroupEntity> findAll() {
        return groupService.findAll();
    }

    @GetMapping("/findById/{id}")
    public Optional<GroupEntity> findById(Long id) {
        return groupService.findById(id);
    }

    @GetMapping("/deleteById/{id}")
    public void deleteById(Long id) {
        groupService.deleteById(id);
    }

    @GetMapping("existsById")
    public boolean existsById(Long id) {
        return groupService.existsById(id);
    }

    @GetMapping("count")
    public long count() {
        return groupService.count();
    }
}

package kr.nyamnyam_kr.controller;


import kr.nyamnyam_kr.model.domain.GroupReplyModel;
import kr.nyamnyam_kr.model.entity.GroupReplyEntity;
import kr.nyamnyam_kr.service.GroupReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group_reply")
public class GroupReplyController {
    private final GroupReplyService groupReplyService;

    @PostMapping("save")
    public GroupReplyEntity save(GroupReplyModel groupReplyModel) {
        return groupReplyService.save(groupReplyModel);
    }

    @GetMapping("findAll")
    public List<GroupReplyEntity> findAll() {
        return groupReplyService.findAll();
    }

    @GetMapping("findById")
    public Optional<GroupReplyEntity> findById(Long id) {
        return groupReplyService.findById(id);
    }

    @GetMapping("deleteById")
    public void deleteById(Long id) {
        groupReplyService.deleteById(id);
    }

    @GetMapping("existsById")
    public boolean existsById(Long id) {
        return groupReplyService.existsById(id);
    }

    @GetMapping("count")
    public long count() {
        return groupReplyService.count();
    }
}

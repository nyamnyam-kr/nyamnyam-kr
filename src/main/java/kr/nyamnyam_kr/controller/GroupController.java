package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.domain.GroupModel;
import kr.nyamnyam_kr.model.entity.GroupEntity;
import kr.nyamnyam_kr.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// put=업데이트,pathvariable patch=> 일부만 수정 post=입력(겟+insert)=>requestbody를 써야함 get=찾아주는,Requestparam delete,


@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/save")
    public Boolean save(@RequestBody GroupModel groupModel) {
        return groupService.save(groupModel);
    }

    @GetMapping("/showGroupOne/{id}")
    public ResponseEntity<?> showGroupOne(@PathVariable Long id) {

        return ResponseEntity.ok(groupService.findById(id));
    }

    @PutMapping("/update/{id}")
    public Boolean update(GroupModel groupModel, @PathVariable Long id) {
        return groupService.update(groupModel);
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

    @GetMapping("/existsById")
    public boolean existsById(Long id) {
        return groupService.existsById(id);
    }

    @GetMapping("/count")
    public long count() {
        return groupService.count();
    }
}

package kr.nyamnyam_kr.controller;


import kr.nyamnyam_kr.model.domain.AttendModel;
import kr.nyamnyam_kr.model.entity.AttendEntity;
import kr.nyamnyam_kr.service.AttendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attends")
public class AttendController {
    
    private final AttendService attendService;

    @PostMapping("/save")
    public ResponseEntity<AttendEntity> save(AttendModel attendModel) {
        return ResponseEntity.ok(attendService.save(attendModel));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AttendEntity>> findAll() {
        return ResponseEntity.ok(attendService.findAll());
    }

    @GetMapping("/findById")
    public ResponseEntity<AttendEntity> findById(Long id) {
        return ResponseEntity.ok(attendService.findById(id).get());
    }

    @GetMapping("/deleteById")
    public void deleteById(Long id) {
        attendService.deleteById(id);
    }

    @GetMapping("/existsById")
    public boolean existsById(Long id) {
        return attendService.existsById(id);
    }

    @GetMapping("/count")
    public long count() {
        return attendService.count();
    }
}

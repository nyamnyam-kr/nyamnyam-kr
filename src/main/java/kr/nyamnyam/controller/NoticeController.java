package kr.nyamnyam.controller;


import kr.nyamnyam.model.domain.NoticeModel;
import kr.nyamnyam.model.entity.NoticeEntity;
import kr.nyamnyam.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class NoticeController {

    private final NoticeService noticeService;


   @GetMapping("")
    public ResponseEntity<List<?>> findAll() {
        return ResponseEntity.ok(noticeService.findAll());
    }

   @GetMapping("/{id}")
    public ResponseEntity<NoticeEntity> findById(@RequestHeader Long id) {
        return ResponseEntity.ok(noticeService.findById(id));
    }


   @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@RequestHeader Long id) {
        return ResponseEntity.ok(noticeService.deleteById(id));
    }

   @PostMapping("")
    public ResponseEntity<NoticeEntity> save(@RequestBody NoticeModel model) {
        return ResponseEntity.ok(noticeService.save(model));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoticeEntity> update(@RequestHeader Long id, @RequestBody NoticeModel model) {
        System.out.println(model);
       return ResponseEntity.ok(noticeService.update(model));
    }
}

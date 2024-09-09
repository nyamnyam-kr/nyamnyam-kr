package kr.nyamnyam.controller;

import kr.nyamnyam.model.entity.ChannelEntity;
import kr.nyamnyam.pattern.proxy.Pagination;
import kr.nyamnyam.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//Post, Put Delete  Get

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/channels")
public class ChannelController {
    private final ChannelService channelService;


    @PostMapping("")
    public ResponseEntity<Boolean> save(@RequestBody ChannelEntity channelEntity) {

        if (channelEntity.getId() == null) {
            channelService.save(channelEntity);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

    @PutMapping("")
    public ResponseEntity<ChannelEntity> update(@RequestBody ChannelEntity channelEntity) {
        return ResponseEntity.ok(channelService.save(channelEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {

        return ResponseEntity.ok(channelService.deleteById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<ChannelEntity>> findAll( ) {
        return ResponseEntity.ok(channelService.findAll());
    }

    @GetMapping("/findAllPerPage/{page}")
    public ResponseEntity<List<ChannelEntity>> findAllPerPage(@PathVariable int page) {
        System.out.println("넘어온 페이지: "+ page);
        return ResponseEntity.ok(channelService.findAllPerPage(page));
    }


    @GetMapping("/crawling")
    public ResponseEntity<List<ChannelEntity>> findCrawling() {
        return ResponseEntity.ok(channelService.crawling());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ChannelEntity>> findById(@PathVariable String id) {
        System.out.println(channelService.findById(id));
        return ResponseEntity.ok(channelService.findById(id));
    }


    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsById(String id) {
        return ResponseEntity.ok(channelService.existsById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(channelService.count());
    }
}

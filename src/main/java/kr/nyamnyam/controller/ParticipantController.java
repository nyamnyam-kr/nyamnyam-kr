package kr.nyamnyam.controller;

import kr.nyamnyam.model.entity.ParticipantEntity;
import kr.nyamnyam.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/participants")
public class ParticipantController {
    private final ParticipantService participantService;


    @PostMapping("")
    public ResponseEntity<Boolean> save(@RequestBody ParticipantEntity participantEntity) {

        if (participantEntity.getId() == null) {
            participantService.save(participantEntity);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

    @PutMapping("")
    public ResponseEntity<ParticipantEntity> update(@RequestBody ParticipantEntity participantEntity) {
        return ResponseEntity.ok(participantService.save(participantEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {

        return ResponseEntity.ok(participantService.deleteById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<ParticipantEntity>> findAll() {
        return ResponseEntity.ok(participantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ParticipantEntity>> findById(@PathVariable String id) {
        return ResponseEntity.ok(participantService.findById(id));
    }


    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsById(String id) {
        return ResponseEntity.ok(participantService.existsById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(participantService.count());
    }
}

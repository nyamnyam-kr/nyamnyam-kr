package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.Participant;
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
    public ResponseEntity<Boolean> save(@RequestBody Participant participant) {

        if (participant.getId() == null) {
            participantService.save(participant);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

    @PutMapping("")
    public ResponseEntity<Participant> update(@RequestBody Participant participant) {
        return ResponseEntity.ok(participantService.save(participant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {

        return ResponseEntity.ok(participantService.deleteById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Participant>> findAll() {
        return ResponseEntity.ok(participantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Participant>> findById(@PathVariable String id) {
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

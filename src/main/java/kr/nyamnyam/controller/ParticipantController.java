package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.Participant;
import kr.nyamnyam.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/participants")
public class ParticipantController {
    private final ParticipantService participantService;

    @PostMapping("")
    public Mono<ResponseEntity<Boolean>> save(@RequestBody Participant participant) {
        return participant.getId() == null
                ? participantService.save(participant)
                .map(savedParticipant -> ResponseEntity.ok(true))
                .defaultIfEmpty(ResponseEntity.ok(false))
                : Mono.just(ResponseEntity.ok(false));
    }

    @PutMapping("")
    public Mono<ResponseEntity<Participant>> update(@RequestBody Participant participant) {
        return participantService.save(participant)
                .map(savedParticipant -> ResponseEntity.ok(savedParticipant));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Boolean>> deleteById(@PathVariable String id) {
        return participantService.deleteById(id)
                .map(deleted -> ResponseEntity.ok(deleted));
    }

    @GetMapping("")
    public Mono<ResponseEntity<Flux<Participant>>> findAll() {
        return Mono.just(ResponseEntity.ok(participantService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Participant>> findById(@PathVariable String id) {
        return participantService.findById(id)
                .map(participant -> ResponseEntity.ok(participant))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/exists")
    public Mono<ResponseEntity<Boolean>> existsById(@RequestParam String id) {
        return participantService.existsById(id)
                .map(exists -> ResponseEntity.ok(exists));
    }

    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> count() {
        return participantService.count()
                .map(count -> ResponseEntity.ok(count));
    }
}

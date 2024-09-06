package kr.nyamnyam.controller;

import kr.nyamnyam.model.entity.MessageEntity;
import kr.nyamnyam.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("")
    public ResponseEntity<Boolean> save(@RequestBody MessageEntity messageEntity) {

        if (messageEntity.getId() == null) {
            messageService.save(messageEntity);
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.ok(false);
    }

    @PutMapping("")
    public ResponseEntity<MessageEntity> update(@RequestBody MessageEntity messageEntity) {
        return ResponseEntity.ok(messageService.save(messageEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {

        return ResponseEntity.ok(messageService.deleteById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<MessageEntity>> findAll() {
        return ResponseEntity.ok(messageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<MessageEntity>> findById(String id) {
        return ResponseEntity.ok(messageService.findById(id));
    }


    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsById(String id) {
        return ResponseEntity.ok(messageService.existsById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return ResponseEntity.ok(messageService.count());
    }
}

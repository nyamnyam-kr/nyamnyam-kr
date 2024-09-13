package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.ReportModel;
import kr.nyamnyam.model.entity.ReportEntity;
import kr.nyamnyam.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("")
    public ResponseEntity<List<?>> findAll() {
        return ResponseEntity.ok(reportService.findAll());

    }

   @GetMapping("/{id}")
    public ResponseEntity<ReportEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.findById(id));
    }


   @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.deleteById(id));
    }

   @PostMapping("/save")
    public ResponseEntity<ReportEntity> save(@RequestBody ReportModel model) {
       System.out.println("model = " + model);
        return ResponseEntity.ok(reportService.save(model));
    }
}

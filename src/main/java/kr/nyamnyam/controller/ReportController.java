package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.ReportModel;
import kr.nyamnyam.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/post")
    public ResponseEntity<Boolean> reportComment(@RequestBody ReportModel reportModel) {
        return ResponseEntity.ok(reportService.save(reportModel));

    }
}

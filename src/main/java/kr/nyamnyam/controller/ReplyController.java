package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.ReplyModel;
import kr.nyamnyam.model.entity.ReplyEntity;
import kr.nyamnyam.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reply/")
public class ReplyController  {
    private final ReplyService replyService;

    @PostMapping("save")
    public ReplyEntity save(ReplyModel replyModel) {
        return replyService.save(replyModel);
    }

    @GetMapping("findAll")
    public List<ReplyEntity> findAll() {
        return replyService.findAll();
    }

    @GetMapping("findById")
    public Optional<ReplyEntity> findById(Long id) {
        return replyService.findById(id);
    }

    @GetMapping("deleteById")
    public void deleteById(Long id) {
        replyService.deleteById(id);
    }

    @GetMapping("existsById")
    public boolean existsById(Long id) {
        return replyService.existsById(id);
    }

    @GetMapping("count")
    public long count() {
        return replyService.count();
    }
}

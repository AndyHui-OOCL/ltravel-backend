package oocl.ltravelbackend.controller;

import lombok.RequiredArgsConstructor;
import oocl.ltravelbackend.model.entity.OfficialComment;
import oocl.ltravelbackend.service.OfficialCommentService;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OfficialCommentController {
    private final OfficialCommentService officialCommentService;
    @GetMapping("/official-comment")
    public OfficialComment getOfficialComment(Long travelPlanId) {
        return officialCommentService.getOfficialCommentByTravelPlanId(travelPlanId).orElse(null);
    }
}

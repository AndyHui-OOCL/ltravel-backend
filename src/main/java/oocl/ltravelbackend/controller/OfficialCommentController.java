package oocl.ltravelbackend.controller;

import lombok.RequiredArgsConstructor;
import oocl.ltravelbackend.common.exception.InvalidTravelPlanIdInputException;
import oocl.ltravelbackend.model.entity.OfficialComment;
import oocl.ltravelbackend.service.OfficialCommentService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OfficialCommentController {
    private final OfficialCommentService officialCommentService;
    @GetMapping("/official-comment/{travelPlanId}")
    public ResponseEntity<OfficialComment> getOfficialComment(@PathVariable Long travelPlanId) {
        if (travelPlanId == null || travelPlanId <= 0) {
            throw new InvalidTravelPlanIdInputException("Travel Plan ID is invalid.");
        }
        return officialCommentService.getOfficialCommentByTravelPlanId(travelPlanId)
                .map(officialComment -> ResponseEntity.ok(officialComment))
                .orElse(ResponseEntity.noContent().build());
    }
}

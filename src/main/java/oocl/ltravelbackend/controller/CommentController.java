package oocl.ltravelbackend.controller;

import lombok.RequiredArgsConstructor;
import oocl.ltravelbackend.common.exception.InvalidTravelComponentIdInputException;
import oocl.ltravelbackend.model.entity.Comment;
import oocl.ltravelbackend.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/comments/{travelComponentId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long travelComponentId) {
        if (travelComponentId == null || travelComponentId <= 0) {
            throw new InvalidTravelComponentIdInputException("Travel Component ID is invalid.");
        }
        return commentService.getCommentsByTravelComponentId(travelComponentId)
                .map(comments -> ResponseEntity.ok(comments))
                .orElse(ResponseEntity.noContent().build());
    }
}

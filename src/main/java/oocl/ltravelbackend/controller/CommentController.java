package oocl.ltravelbackend.controller;

import lombok.RequiredArgsConstructor;
import oocl.ltravelbackend.model.dto.CommentReqDTO;
import oocl.ltravelbackend.model.dto.CommentRespDTO;
import oocl.ltravelbackend.model.dto.PageResult;
import oocl.ltravelbackend.model.entity.Comment;
import oocl.ltravelbackend.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/comments/{travelComponentId}")
    public ResponseEntity<List<CommentRespDTO>> getCommentsByTravelComponentId(@PathVariable Long travelComponentId) {

        return ResponseEntity.ok(commentService.getCommentsByTravelComponentId(travelComponentId));
    }
    @GetMapping("/comments")
    public ResponseEntity<PageResult<CommentRespDTO>> pageCommentsByTravelPlanId(@RequestParam("travelPlanId") Long travelPlanId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "9") int size) {

        Page<Comment> commentPage = commentService.getPaginatedCommentsByTravelPlanId(travelPlanId, page, size);
        List<CommentRespDTO> dtoList = commentPage.getContent().stream().map(comment -> CommentRespDTO.builder()
                .id(comment.getId())
                .travelComponentName(comment.getTravelComponent().getName())
                .username(comment.getUser().getUserName())
                .description(comment.getDescription())
                .isLike(comment.getIsLike())
                .build()).toList();

        PageResult<CommentRespDTO> result = new PageResult<>(dtoList, commentPage.getTotalElements());
        return ResponseEntity.ok(result);
    }
}

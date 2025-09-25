package oocl.ltravelbackend.service;

import lombok.RequiredArgsConstructor;
import oocl.ltravelbackend.common.exception.InvalidCommentPaginationInputException;
import oocl.ltravelbackend.common.exception.InvalidTravelComponentIdInputException;
import oocl.ltravelbackend.common.exception.InvalidTravelPlanIdInputException;
import oocl.ltravelbackend.model.dto.CommentReqDTO;
import oocl.ltravelbackend.model.dto.CommentRespDTO;
import oocl.ltravelbackend.model.entity.Comment;
import oocl.ltravelbackend.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentRespDTO> getCommentsByTravelComponentId(Long travelComponentId) {
        if (travelComponentId == null || travelComponentId <= 0) {
            throw new InvalidTravelComponentIdInputException("Travel Component ID is invalid.");
        }
        List<Comment> comments = commentRepository.findByTravelComponentId(travelComponentId);
        return comments==null || comments.isEmpty() ? new ArrayList<>() : comments.stream()
                .map(comment -> CommentRespDTO.builder()
                        .id(comment.getId())
                        .travelComponentName(comment.getTravelComponent().getName())
                        .username(comment.getUser().getUserName())
                        .description(comment.getDescription())
                        .isLike(comment.getIsLike())
                        .build())
                .toList();
    }
    public Page<Comment> getPaginatedCommentsByTravelPlanId(Long travelPlanId, int page, int size) {

        if (travelPlanId == null || travelPlanId <= 0) {
            throw new InvalidTravelPlanIdInputException("Travel Component ID is invalid.");
        }
        if (page < 1 || size <= 0) {
            throw new InvalidCommentPaginationInputException("Page number must be greater than 0 and page size must be greater than zero");
        }
        return commentRepository.findByTravelComponentIdPaginated(travelPlanId, page - 1, size);
    }
}

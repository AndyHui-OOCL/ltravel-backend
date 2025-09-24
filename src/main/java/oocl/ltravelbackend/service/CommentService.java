package oocl.ltravelbackend.service;

import lombok.RequiredArgsConstructor;
import oocl.ltravelbackend.common.exception.InvalidTravelComponentIdInputException;
import oocl.ltravelbackend.model.entity.Comment;
import oocl.ltravelbackend.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getCommentsByTravelComponentId(Long travelComponentId) {
        if (travelComponentId == null || travelComponentId <= 0) {
            throw new InvalidTravelComponentIdInputException("Travel Component ID is invalid.");
        }
        List<Comment> comments = commentRepository.findByTravelComponentId(travelComponentId);
        return comments == null || comments.isEmpty() ? new ArrayList<>() : comments;
    }
}

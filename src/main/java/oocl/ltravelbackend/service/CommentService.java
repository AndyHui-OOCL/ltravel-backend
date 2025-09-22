package oocl.ltravelbackend.service;

import lombok.RequiredArgsConstructor;
import oocl.ltravelbackend.model.entity.Comment;
import oocl.ltravelbackend.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Optional<List<Comment>> getCommentsByTravelComponentId(Long travelComponentId) {
        List<Comment> comments = commentRepository.findByTravelComponentId(travelComponentId);
        return comments.isEmpty() ? Optional.empty() : Optional.of(comments);
    }
}

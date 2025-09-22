package oocl.ltravelbackend.repository.Impl;

import lombok.RequiredArgsConstructor;
import oocl.ltravelbackend.model.entity.Comment;
import oocl.ltravelbackend.repository.CommentRepository;
import oocl.ltravelbackend.repository.dao.CommentJPARepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private final CommentJPARepository commentJPARepository;

    @Override
    public void deleteAll() {
        commentJPARepository.deleteAll();
    }

    @Override
    public Comment create(Comment comment) {
        return commentJPARepository.save(comment);
    }

    @Override
    public List<Comment> findByTravelComponentId(Long travelComponentId) {
        return commentJPARepository.findByTravelComponentId(travelComponentId);
    }


}

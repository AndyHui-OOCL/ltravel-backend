package oocl.ltravelbackend.repository;

import oocl.ltravelbackend.model.entity.Comment;

import java.util.List;

public interface CommentRepository {
    void deleteAll();

    Comment create(Comment comment);

    List<Comment> findByTravelComponentId(Long travelComponentId);

}

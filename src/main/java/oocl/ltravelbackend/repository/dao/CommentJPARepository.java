package oocl.ltravelbackend.repository.dao;

import oocl.ltravelbackend.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJPARepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTravelComponentId(Long travelComponentId);
}

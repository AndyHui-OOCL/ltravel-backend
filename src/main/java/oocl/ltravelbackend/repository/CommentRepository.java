package oocl.ltravelbackend.repository;

import oocl.ltravelbackend.model.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentRepository {
    void deleteAll();

    Comment create(Comment comment);

    List<Comment> findByTravelComponentId(Long travelComponentId);


    Page<Comment> findByTravelComponentIdPaginated(Long travelPlanId, int page, int size);
}

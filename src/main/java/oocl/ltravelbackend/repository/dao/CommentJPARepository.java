package oocl.ltravelbackend.repository.dao;

import oocl.ltravelbackend.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentJPARepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTravelComponentId(Long travelComponentId);

    @Query(value = "SELECT * FROM comment WHERE travel_component_id = :travelComponentId ORDER BY id LIMIT :limit", nativeQuery = true)
    List<Comment> findByTravelComponentIdLimit(@Param("travelComponentId") Long travelComponentId, @Param("limit") Integer limit);
}

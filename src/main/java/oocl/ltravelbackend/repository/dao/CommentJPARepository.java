package oocl.ltravelbackend.repository.dao;

import oocl.ltravelbackend.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentJPARepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTravelComponentId(Long travelComponentId);


    @Query(value = "select * from comment where comment.travel_component_id in (\n" +
            "    select travel_days.travel_component_id from travel_days\n" +
            "    where travel_days.travel_plan_id=:travelPlanId \n" +
            ") " ,
            nativeQuery = true)
    Page<Comment> findByTravelPlanId(@Param("travelPlanId") Long travelPlanId, Pageable pageable);
}

package oocl.ltravelbackend.repository.dao;

import oocl.ltravelbackend.model.entity.OfficialComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficialCommentJPARepository extends JpaRepository<OfficialComment, Long> {

    OfficialComment findByTravelPlanId(Long travelPlanId);
}

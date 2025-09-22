package oocl.ltravelbackend.repository;

import oocl.ltravelbackend.model.entity.OfficialComment;

public interface OfficialCommentRepository {
    OfficialComment findByTravelPlanId(Long travelPlanId);
    OfficialComment save(OfficialComment officialComment);
    void deleteAll();
}

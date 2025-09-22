package oocl.ltravelbackend.repository.Impl;

import lombok.RequiredArgsConstructor;
import oocl.ltravelbackend.model.entity.OfficialComment;
import oocl.ltravelbackend.repository.OfficialCommentRepository;
import oocl.ltravelbackend.repository.dao.OfficialCommentJPARepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OfficialCommentRepositoryImpl implements OfficialCommentRepository {
    private final OfficialCommentJPARepository officialCommentJPARepository;

    @Override
    public OfficialComment findByTravelPlanId(Long travelPlanId) {
        return officialCommentJPARepository.findByTravelPlanId(travelPlanId);
    }

    @Override
    public OfficialComment save(OfficialComment officialComment) {
        return officialCommentJPARepository.save(officialComment);
    }

    @Override
    public void deleteAll() {
        officialCommentJPARepository.deleteAll();
    }
}

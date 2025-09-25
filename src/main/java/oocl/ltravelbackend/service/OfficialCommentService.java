package oocl.ltravelbackend.service;

import lombok.RequiredArgsConstructor;
import oocl.ltravelbackend.common.exception.InvalidTravelPlanIdInputException;
import oocl.ltravelbackend.model.entity.OfficialComment;
import oocl.ltravelbackend.repository.OfficialCommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfficialCommentService {
    private final OfficialCommentRepository officialCommentRepository;

    public OfficialComment getOfficialCommentByTravelPlanId(Long travelPlanId) {
        if (travelPlanId == null || travelPlanId <= 0) {
            throw new InvalidTravelPlanIdInputException("Travel Plan ID is invalid.");
        }
        return officialCommentRepository.findByTravelPlanId(travelPlanId);
    }

}

package oocl.ltravelbackend.service;

import oocl.ltravelbackend.common.exception.InvalidTravelPlanIdInputException;
import oocl.ltravelbackend.model.entity.OfficialComment;
import oocl.ltravelbackend.repository.OfficialCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
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

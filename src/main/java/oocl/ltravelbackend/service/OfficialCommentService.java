package oocl.ltravelbackend.service;

import oocl.ltravelbackend.model.entity.OfficialComment;
import oocl.ltravelbackend.repository.OfficialCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class OfficialCommentService {
    private final OfficialCommentRepository officialCommentRepository;
    public Optional<OfficialComment> getOfficialCommentByTravelPlanId(Long travelPlanId) {
        return Optional.ofNullable(officialCommentRepository.findByTravelPlanId(travelPlanId));
    }

}

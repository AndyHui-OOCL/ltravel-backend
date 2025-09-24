package oocl.ltravelbackend.service;

import oocl.ltravelbackend.common.exception.TravelPlanNotFoundException;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.model.entity.User;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import oocl.ltravelbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private TravelPlanRepository travelPlanRepository;

    @Autowired
    private UserRepository userRepository;

    public String likeTravelPlan(Long planId, Long userId) {
        TravelPlan travelPlan = travelPlanRepository.getTravelPlanDetailById(planId);
        User user = userRepository.findUserById(userId);
        if (travelPlan == null) {
            throw new TravelPlanNotFoundException("Travel Plan not found");
        }
        user.getSavedTravelPlans().add(travelPlan);
        return "Travel Plan is saved successfully";
    }
}

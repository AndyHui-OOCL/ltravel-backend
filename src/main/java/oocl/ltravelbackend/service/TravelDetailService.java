package oocl.ltravelbackend.service;

import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelDetailService {

    @Autowired
    private TravelPlanRepository travelPlanRepository;

    public TravelPlan getTravelPlan(Long id) {
        TravelPlan travelPlan = travelPlanRepository.getTravelPlanDetailById(id);
        if(travelPlan == null) {
            throw new RuntimeException("Travel plan not found with id: " + id);
        }
        return travelPlan;
    }
}

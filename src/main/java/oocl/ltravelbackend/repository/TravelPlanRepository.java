package oocl.ltravelbackend.repository;

import oocl.ltravelbackend.model.dto.BasicTravelPlanRes;
import oocl.ltravelbackend.model.entity.TravelPlan;

public interface TravelPlanRepository {
    BasicTravelPlanRes getPaginatedBasicTravelPlans(int page, int size);

    TravelPlan getTravelPlanDetailById(Long id);
}

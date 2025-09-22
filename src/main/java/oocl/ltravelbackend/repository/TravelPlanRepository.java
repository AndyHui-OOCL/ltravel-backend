package oocl.ltravelbackend.repository;

import oocl.ltravelbackend.model.dto.BasicTravelPlanRes;

public interface TravelPlanRepository {
    BasicTravelPlanRes getPaginatedBasicTravelPlans(int page, int size);
}

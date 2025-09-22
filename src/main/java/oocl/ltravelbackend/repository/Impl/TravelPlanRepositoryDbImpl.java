package oocl.ltravelbackend.repository.Impl;

import oocl.ltravelbackend.model.dto.BasicTravelPlanRes;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TravelPlanRepositoryDbImpl implements TravelPlanRepository {
    @Override
    public BasicTravelPlanRes getPaginatedBasicTravelPlans(int page, int size){
        return null;
    }
}

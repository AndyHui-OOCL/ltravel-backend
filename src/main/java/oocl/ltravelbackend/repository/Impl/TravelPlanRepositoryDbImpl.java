package oocl.ltravelbackend.repository.Impl;

import oocl.ltravelbackend.model.dto.BasicTravelPlanRes;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import oocl.ltravelbackend.repository.dao.TravelPlanJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TravelPlanRepositoryDbImpl implements TravelPlanRepository {

    @Autowired
    private TravelPlanJpaRepository travelPlanJpaRepository;

    @Override
    public BasicTravelPlanRes getPaginatedBasicTravelPlans(int page, int size){
        return null;
    }

    @Override
    public TravelPlan getTravelPlanDetailById(Long id) {
        return travelPlanJpaRepository.findById(id).orElse(null);
    }

}

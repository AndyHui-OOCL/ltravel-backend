package oocl.ltravelbackend.repository.Impl;

import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import oocl.ltravelbackend.repository.dao.TravelPlanJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TravelPlanRepositoryDbImpl implements TravelPlanRepository {
    @Autowired
    private TravelPlanJpaRepository travelPlanJpaRepository;

    @Override
    public TravelPlan getTravelPlanDetailById(Long id) {
        return travelPlanJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<TravelPlan> getAllFilteredTravelPlans(String city, String tag) {
        return travelPlanJpaRepository.findFilteredTravelPlans(city, tag, Pageable.unpaged()).getContent();
    }
}

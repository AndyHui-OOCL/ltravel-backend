package oocl.ltravelbackend.repository.Impl;

import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import oocl.ltravelbackend.repository.dao.TravelPlanJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
public class TravelPlanRepositoryDbImpl implements TravelPlanRepository {
    @Autowired
    private TravelPlanJpaRepository travelPlanJpaRepository;

    @Override
    public Page<TravelPlan> findTravelPlansByPagination(Pageable pageParams) {
        return travelPlanJpaRepository.findAll(pageParams);
    }

    @Override
    public TravelPlan getTravelPlanDetailById(Long id) {
        return travelPlanJpaRepository.findById(id).orElse(null);
    }
}

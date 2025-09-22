package oocl.ltravelbackend.repository;

import oocl.ltravelbackend.model.entity.TravelPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TravelPlanRepository {
    Page<TravelPlan> findTravelPlansByPagination(Pageable pageParams);

    TravelPlan getTravelPlanDetailById(Long id);
}

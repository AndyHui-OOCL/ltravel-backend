package oocl.ltravelbackend.repository;

import oocl.ltravelbackend.model.entity.TravelPlan;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TravelPlanRepository {
    List<TravelPlan> findTravelPlansByPagination(Pageable pageParams);

    Integer findNumOfTravelPlan();

    TravelPlan getTravelPlanDetailById(Long id);
}

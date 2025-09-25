package oocl.ltravelbackend.repository;

import oocl.ltravelbackend.model.entity.TravelPlan;

import java.util.List;

public interface TravelPlanRepository {

    TravelPlan getTravelPlanDetailById(Long id);

    List<TravelPlan> getAllFilteredTravelPlans(String city, String tag);

    List<TravelPlan> findAll();
}

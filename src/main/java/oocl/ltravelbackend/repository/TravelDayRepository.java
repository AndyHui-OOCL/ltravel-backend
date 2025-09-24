package oocl.ltravelbackend.repository;

import oocl.ltravelbackend.model.entity.TravelDay;

import java.util.List;

public interface TravelDayRepository {
    List<TravelDay> findAllByTravelPlanId(Long travelPlanId);
}

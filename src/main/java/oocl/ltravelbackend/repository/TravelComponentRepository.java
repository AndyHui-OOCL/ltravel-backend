package oocl.ltravelbackend.repository;

import oocl.ltravelbackend.model.entity.TravelComponent;

import java.util.List;

public interface TravelComponentRepository {
    void deleteAll();

    TravelComponent getTravelComponentDetailById(Long id);

    TravelComponent save(TravelComponent travelComponent);

    List<TravelComponent> getTravelComponentsByTravelDayIds(List<Long> travelComponentIds);

    TravelComponent findById(Long id);
}

package oocl.ltravelbackend.repository;

import oocl.ltravelbackend.model.entity.TravelComponent;

public interface TravelComponentRepository {
    void deleteAll();
    TravelComponent getTravelComponentDetailById(Long id);
    TravelComponent save(TravelComponent travelComponent);
}

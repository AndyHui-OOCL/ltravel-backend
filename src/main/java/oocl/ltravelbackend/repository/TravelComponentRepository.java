package oocl.ltravelbackend.repository;

import java.util.List;
import oocl.ltravelbackend.model.entity.TravelComponent;
import oocl.ltravelbackend.model.entity.TravelDay;

public interface TravelComponentRepository {
    void deleteAll();
    TravelComponent getTravelComponentDetailById(Long id);
    TravelComponent save(TravelComponent travelComponent);
    List<TravelComponent> getTravelComponentsByTravelDayIds(List<Long> travelComponentIds);
}

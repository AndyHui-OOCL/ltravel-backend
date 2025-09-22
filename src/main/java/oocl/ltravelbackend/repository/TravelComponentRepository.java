package oocl.ltravelbackend.repository;

import oocl.ltravelbackend.model.entity.TravelComponent;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.dao.TravelComponentJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface TravelComponentRepository {
    TravelComponent getTravelComponentDetailById(Long id);
}

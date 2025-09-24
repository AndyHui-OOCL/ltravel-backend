package oocl.ltravelbackend.repository.dao;

import oocl.ltravelbackend.model.entity.TravelDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelDayJpaRepository extends JpaRepository<TravelDay, Long> {
    List<TravelDay> findAllByTravelPlanId(Long travelPlanId);
}

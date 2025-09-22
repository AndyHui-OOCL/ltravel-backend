package oocl.ltravelbackend.repository.dao;

import oocl.ltravelbackend.model.entity.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelPlanJpaRepository extends JpaRepository<TravelPlan, Integer> {
}

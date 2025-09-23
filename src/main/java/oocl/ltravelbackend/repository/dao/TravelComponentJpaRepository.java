package oocl.ltravelbackend.repository.dao;

import oocl.ltravelbackend.model.entity.TravelComponent;
import oocl.ltravelbackend.model.entity.TravelDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelComponentJpaRepository extends JpaRepository<TravelComponent, Long> {
}

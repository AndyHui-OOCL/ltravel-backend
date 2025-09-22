package oocl.ltravelbackend.repository.dao;

import java.util.List;
import oocl.ltravelbackend.model.entity.TravelDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelDayJpaRepository extends JpaRepository<TravelDay, Long> {

}

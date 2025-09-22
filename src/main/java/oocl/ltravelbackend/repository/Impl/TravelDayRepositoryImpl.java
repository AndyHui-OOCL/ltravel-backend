package oocl.ltravelbackend.repository.Impl;

import java.util.List;
import java.util.stream.Collectors;
import oocl.ltravelbackend.model.entity.TravelDay;
import oocl.ltravelbackend.repository.TravelDayRepository;
import oocl.ltravelbackend.repository.dao.TravelDayJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TravelDayRepositoryImpl implements TravelDayRepository {

  @Autowired
  private TravelDayJpaRepository travelDayJpaRepository;


  @Override
  public List<TravelDay> getTravelDayIdsByTravelPlanId(Long travelPlanId) {
    return travelDayJpaRepository.findByTravelPlanId(travelPlanId);
  }
}

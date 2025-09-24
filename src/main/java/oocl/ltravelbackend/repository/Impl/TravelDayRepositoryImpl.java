package oocl.ltravelbackend.repository.Impl;

import oocl.ltravelbackend.model.entity.TravelDay;
import oocl.ltravelbackend.repository.TravelDayRepository;
import oocl.ltravelbackend.repository.dao.TravelDayJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TravelDayRepositoryImpl implements TravelDayRepository {

    @Autowired
    private TravelDayJpaRepository travelDayJpaRepository;

    @Override
    public List<TravelDay> findAllByTravelPlanId(Long travelPlanId) {
        return travelDayJpaRepository.findAllByTravelPlanId(travelPlanId);
    }

}

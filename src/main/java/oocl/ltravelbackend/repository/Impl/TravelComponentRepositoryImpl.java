package oocl.ltravelbackend.repository.Impl;

import java.util.List;

import oocl.ltravelbackend.model.entity.TravelComponent;
import oocl.ltravelbackend.model.entity.TravelDay;
import oocl.ltravelbackend.repository.TravelComponentRepository;
import oocl.ltravelbackend.repository.dao.TravelComponentJpaRepository;
import oocl.ltravelbackend.repository.dao.TravelDayJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TravelComponentRepositoryImpl implements TravelComponentRepository {

    @Autowired
    private TravelComponentJpaRepository travelComponentJpaRepository;

    @Override
    public void deleteAll() {
        travelComponentJpaRepository.deleteAll();
    }

    @Override
    public TravelComponent getTravelComponentDetailById(Long id) {
        return travelComponentJpaRepository.findById(id).orElse(null);
    }

    @Override
    public TravelComponent save(TravelComponent travelComponent) {
        return travelComponentJpaRepository.save(travelComponent);
    }

    @Override
    public List<TravelComponent> getTravelComponentsByTravelDayIds(List<Long> travelComponentIds) {
        return travelComponentJpaRepository.findAllById(travelComponentIds);
    }

    @Override
    public TravelComponent findById(Long id) {
        return travelComponentJpaRepository.findById(id).orElse(null);
    }

}

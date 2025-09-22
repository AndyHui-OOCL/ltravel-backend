package oocl.ltravelbackend.repository.Impl;

import oocl.ltravelbackend.model.entity.TravelComponent;
import oocl.ltravelbackend.repository.TravelComponentRepository;
import oocl.ltravelbackend.repository.dao.TravelComponentJpaRepository;
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
}

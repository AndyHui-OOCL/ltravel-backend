package oocl.ltravelbackend.repository.Impl;

import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.AIChatRepository;
import oocl.ltravelbackend.repository.dao.AIChatJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AIChatRepositoryImpl implements AIChatRepository {

    @Autowired
    private AIChatJpaRepository aiChatJpaRepository;

    @Override
    public List<TravelPlan> findChatByIds(List<Long> ids) {
        return aiChatJpaRepository.findAllById(ids);
    }
}

package oocl.ltravelbackend.repository;

import oocl.ltravelbackend.model.entity.TravelPlan;

import java.util.List;

public interface AIChatRepository {
    List<TravelPlan> findChatByIds(List<Long> ids);
}

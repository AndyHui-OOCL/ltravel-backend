package oocl.ltravelbackend.service;

import oocl.ltravelbackend.common.exception.InvalidTravelPlanPaginationInput;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TravelPlanService {

  @Autowired
  private TravelPlanRepository travelPlanRepository;

  public Page<TravelPlan> getPaginatedBasicTravelPlans(int page, int size) {
    if (page < 0 || size <= 0) {
      throw new InvalidTravelPlanPaginationInput(
        "Page number cannot be negative and page size must be grater than zero");
    }
    return travelPlanRepository.findTravelPlansByPagination(PageRequest.of(page, size));
  }

  public TravelPlan getTravelPlanDetailById(Long id) {
    return travelPlanRepository.getTravelPlanDetailById(id);
  }
}

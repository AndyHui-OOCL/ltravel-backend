package oocl.ltravelbackend.service;

import oocl.ltravelbackend.common.InvalidTravelPlanPaginationInput;
import oocl.ltravelbackend.model.dto.BasicTravelPlanRes;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelPlanService {

  @Autowired
  private TravelPlanRepository travelPlanRepository;

  public BasicTravelPlanRes getPaginatedBasicTravelPlans(int page, int size) {
    if (page < 0 || size <= 0) {
      throw new InvalidTravelPlanPaginationInput(
        "Page number cannot be negative and page size must be grater than zero");
    }
    return travelPlanRepository.getPaginatedBasicTravelPlans(page, size);
  }

  public TravelPlan getTravelPlanDetailById(Long id) {
    return travelPlanRepository.getTravelPlanDetailById(id);
  }
}

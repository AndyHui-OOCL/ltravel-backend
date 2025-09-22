package oocl.ltravelbackend.service;

import java.util.List;
import oocl.ltravelbackend.common.exception.InvalidTravelPlanPaginationInput;
import oocl.ltravelbackend.model.dto.BasicTravelPlanRes;
import oocl.ltravelbackend.model.dto.TravelPlanDetailDTO;
import oocl.ltravelbackend.model.entity.TravelDay;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.TravelDayRepository;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TravelPlanService {

  @Autowired
  private TravelPlanRepository travelPlanRepository;

  @Autowired
  private TravelDayRepository travelDayRepository;

  public Page<TravelPlan> getPaginatedBasicTravelPlans(int page, int size) {
    if (page < 0 || size <= 0) {
      throw new InvalidTravelPlanPaginationInput(
        "Page number cannot be negative and page size must be grater than zero");
    }
    return travelPlanRepository.findTravelPlansByPagination(PageRequest.of(page, size));
  }

  public TravelPlanDetailDTO getTravelPlanDetailById(Long id) {
    TravelPlanDetailDTO travelPlanDetailDTO = new TravelPlanDetailDTO();
    TravelPlan travelPlan = travelPlanRepository.getTravelPlanDetailById(id);
    if (travelPlan != null) {
      travelPlanDetailDTO.setTitle(travelPlan.getTitle());
      travelPlanDetailDTO.setDescription(travelPlan.getDescription());
      travelPlanDetailDTO.setPopular(travelPlan.isPopular());
      travelPlanDetailDTO.setLocalTravel(travelPlan.isLocalTravel());
    }
    //setRoute
    List<TravelDay> TravelDayList = travelDayRepository.getTravelDayIdsByTravelPlanId(id);

    return null;
  }
}

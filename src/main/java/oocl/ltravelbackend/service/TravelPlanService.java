package oocl.ltravelbackend.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import oocl.ltravelbackend.common.exception.InvalidTravelPlanPaginationInputException;
import oocl.ltravelbackend.model.dto.TravelPlanDetailDTO;
import oocl.ltravelbackend.model.dto.TravelPlanOverviewDto;
import oocl.ltravelbackend.model.entity.TravelComponent;
import oocl.ltravelbackend.model.entity.TravelDay;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.TravelComponentRepository;
import oocl.ltravelbackend.repository.TravelDayRepository;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TravelPlanService {

  @Autowired
  private TravelPlanRepository travelPlanRepository;

  @Autowired
  private TravelDayRepository travelDayRepository;

  @Autowired
  private TravelComponentRepository travelComponentRepository;

  public List<TravelPlanOverviewDto> getPaginatedBasicTravelPlans(int page, int size) {
    if (page < 1 || size <= 0) {
      throw new InvalidTravelPlanPaginationInputException(
        "Page number must be greater than 0 and page size must be greater than zero");
    }
    List<TravelPlan> result = travelPlanRepository.findTravelPlansByPagination(
      PageRequest.of(page - 1, size));
    return result.stream().map(travelPlan -> {
      int highestDay = travelPlan.getTravelDays().stream()
        .mapToInt(TravelDay::getDayNum)  // Assuming there's a getDay() method in TravelDay
        .max()
        .orElse(0);  // Default to 0 if no travel days exist

      return TravelPlanOverviewDto.builder()
        .id(travelPlan.getId())
        .cityName(travelPlan.getCityName())
        .description(travelPlan.getDescription())
        .totalTravelDay(highestDay)
        .totalTravelComponent(travelPlan.getTravelDays().size())
        .travePlanPlanImages(travelPlan.getImages())
        .build();
    }).toList();
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
    List<TravelDay> travelDays = travelDayRepository.getTravelDaysByTravelPlanId(id);
    if (travelDays != null && !travelDays.isEmpty()) {
      int totalTravelDay = travelDays.stream()
        .mapToInt(TravelDay::getDayNum)
        .max()
        .orElse(0);
      travelPlanDetailDTO.setTotalTravelDay(totalTravelDay);
      travelPlanDetailDTO.setTotalTravelComponent(travelDays.size());

      List<Long> componentIds = travelDays.stream()
        .map(TravelDay::getTravelComponentId).collect(Collectors.toList());
      List<TravelComponent> travelComponents = travelComponentRepository.getTravelComponentsByTravelDayIds(
        componentIds);
      Map<Long, String> componentIdAndNameMap;
      if (travelComponents != null && !travelComponents.isEmpty()) {
        componentIdAndNameMap = travelComponents.stream()
          .collect(Collectors.toMap(TravelComponent::getId, TravelComponent::getName));
      } else {
        componentIdAndNameMap = new HashMap<>();
      }
      Map<Integer, List<String>> route = travelDays.stream()
        .sorted(Comparator.comparing(TravelDay::getComponentOrder))
        .collect(Collectors.groupingBy(
          TravelDay::getDayNum,
          LinkedHashMap::new,
          Collectors.mapping(
            day -> componentIdAndNameMap.get(day.getTravelComponentId()),
            Collectors.toList()
          )
        ));
      travelPlanDetailDTO.setRoute(route);
    } else {
      travelPlanDetailDTO.setTotalTravelDay(0);
      travelPlanDetailDTO.setTotalTravelComponent(0);
    }

    //todo set location images
    return travelPlanDetailDTO;
  }
}

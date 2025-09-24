package oocl.ltravelbackend.service;

import oocl.ltravelbackend.common.exception.InvalidTravelPlanIdInputException;
import oocl.ltravelbackend.common.exception.InvalidTravelPlanPaginationInputException;
import oocl.ltravelbackend.model.dto.TravelLocationEventDTO;
import oocl.ltravelbackend.model.dto.TravelPlanDetailDTO;
import oocl.ltravelbackend.model.dto.TravelPlanOverviewDto;
import oocl.ltravelbackend.model.entity.ComponentImage;
import oocl.ltravelbackend.model.entity.PlanImage;
import oocl.ltravelbackend.model.entity.TravelDay;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TravelPlanService {

    @Autowired
    private TravelPlanRepository travelPlanRepository;

    public List<TravelPlanOverviewDto> getFilteredPaginatedTravelPlans(String city, Integer travelDays, String tag, int page, int size){
        if (page < 1 || size <= 0) {
            throw new InvalidTravelPlanPaginationInputException(
                    "Page number must be greater than 0 and page size must be greater than zero");
        }

        List<TravelPlan> allFilteredTravelPlans = travelPlanRepository.getAllFilteredTravelPlans(city, tag);

        if(travelDays != null && travelDays > 0) {
            allFilteredTravelPlans = allFilteredTravelPlans.stream()
                    .filter(travelPlan -> travelPlan.getTravelDays().stream()
                            .mapToInt(TravelDay::getDayNum)
                            .max()
                            .orElse(0) == travelDays)
                    .toList();
        }

        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, allFilteredTravelPlans.size());

        if (startIndex >= allFilteredTravelPlans.size()) {
            return List.of();
        }

        List<TravelPlan> paginatedTravelPlans = allFilteredTravelPlans.subList(startIndex, endIndex);

        return paginatedTravelPlans.stream()
                .map(travelPlan -> {
                            int highestDay = travelPlan.getTravelDays().stream()
                                    .mapToInt(TravelDay::getDayNum)
                                    .max()
                                    .orElse(0);

                            return TravelPlanOverviewDto.builder()
                                    .id(travelPlan.getId())
                                    .cityName(travelPlan.getCityName())
                                    .title(travelPlan.getTitle())
                                    .totalTravelDay(highestDay)
                                    .totalTravelComponent(travelPlan.getTravelDays().size())
                                    .travePlanPlanImages(travelPlan.getImages())
                                    .build();
                        }
                ).toList();
    }

    public Integer getNumOfTravelPlans(String city, Integer travelDays, String tag) {
        List<TravelPlan> allFilteredTravelPlans = travelPlanRepository.getAllFilteredTravelPlans(city, tag);

        if(travelDays != null && travelDays > 0) {
            allFilteredTravelPlans = allFilteredTravelPlans.stream()
                    .filter(travelPlan -> travelPlan.getTravelDays().stream()
                            .mapToInt(TravelDay::getDayNum)
                            .max()
                            .orElse(0) == travelDays)
                    .toList();
        }
        return allFilteredTravelPlans.size();
    }

    public TravelPlanDetailDTO getTravelPlanDetailById(Long id) {
        TravelPlan travelPlan = travelPlanRepository.getTravelPlanDetailById(id);
        if (travelPlan == null) {
            throw new InvalidTravelPlanIdInputException("Travel Plan Id is not found");
        }

        int highestDay = travelPlan.getTravelDays().stream()
                .mapToInt(TravelDay::getDayNum)
                .max()
                .orElse(0);

        List<String> planImages = travelPlan.getImages().stream()
                .map(PlanImage::getUrl)
                .collect(Collectors.toList());

        List<TravelDay> travelDays = travelPlan.getTravelDays();
        Map<Integer, List<String>> route = travelDays.stream()
                .sorted(Comparator.comparing(TravelDay::getComponentOrder))
                .collect(Collectors.groupingBy(
                        TravelDay::getDayNum,
                        LinkedHashMap::new,
                        Collectors.mapping(
                                day -> day.getTravelComponent() != null ? day.getTravelComponent().getName() : "",
                                Collectors.toList()
                        )
                ));

        List<TravelLocationEventDTO> travelLocationEvents = travelDays.stream().filter(day -> day.getTravelComponent().getIsLocation() == true)
                .map(day -> TravelLocationEventDTO.builder()
                        .eventName(day.getTravelComponent().getName())
                        .description(day.getTravelComponent().getDescription())
                        .locationImages(day.getTravelComponent().getImages().stream()
                                .map(ComponentImage::getUrl)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        return TravelPlanDetailDTO.builder()
                .title(travelPlan.getTitle())
                .description(travelPlan.getDescription())
                .isPopular(travelPlan.isPopular())
                .isLocalTravel(travelPlan.isLocalTravel())
                .totalTravelDay(highestDay)
                .planImages(planImages)
                .totalTravelComponent(travelPlan.getTravelDays().size())
                .route(route)
                .travelLocationEvents(travelLocationEvents)
                .build();

    }
}

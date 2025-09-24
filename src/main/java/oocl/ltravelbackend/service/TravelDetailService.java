package oocl.ltravelbackend.service;

import oocl.ltravelbackend.common.exception.InvalidTravelPlanIdInputException;
import oocl.ltravelbackend.model.dto.TravelComponentDto;
import oocl.ltravelbackend.model.dto.TravelRouteListDto;
import oocl.ltravelbackend.model.entity.TravelComponent;
import oocl.ltravelbackend.model.entity.TravelDay;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.TravelComponentRepository;
import oocl.ltravelbackend.repository.TravelDayRepository;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TravelDetailService {

    @Autowired
    private TravelPlanRepository travelPlanRepository;
    @Autowired
    private TravelComponentRepository travelComponentRepository;
    @Autowired
    private TravelDayRepository travelDayRepository;

    public TravelPlan getTravelPlan(Long id) {
        TravelPlan travelPlan = travelPlanRepository.getTravelPlanDetailById(id);
        if (travelPlan == null) {
            throw new RuntimeException("Travel plan not found with id: " + id);
        }
        return travelPlan;
    }

    public TravelComponent getTravelComponent(Long id) {
        TravelComponent travelComponent = travelComponentRepository.getTravelComponentDetailById(id);
        if (travelComponent == null) {
            throw new RuntimeException("Travel component not found with id: " + id);
        }
        return travelComponent;
    }

    public List<TravelDay> getTravelDayList(Long travelPlanId) {
        return travelDayRepository.findAllByTravelPlanId(travelPlanId);
    }

    public TravelRouteListDto getTravelDayDetailById(Long id) {
        TravelPlan travelPlan = travelPlanRepository.getTravelPlanDetailById(id);
        if (travelPlan == null) {
            throw new InvalidTravelPlanIdInputException("Travel Plan Id is not found");
        }

        int highestDay = travelPlan.getTravelDays().stream()
                .mapToInt(TravelDay::getDayNum)
                .max()
                .orElse(0);

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

        List<TravelComponentDto> travelLocationEvents = travelDays.stream()
                .map(day -> TravelComponentDto.builder()
                        .id(day.getTravelComponent().getId())
                        .name(day.getTravelComponent().getName())
                        .description(day.getTravelComponent().getDescription())
                        .images(day.getTravelComponent().getImages())
                        .address(day.getTravelComponent().getAddress())
                        .openTime(day.getTravelComponent().getOpenTime())
                        .rating(day.getTravelComponent().getRating())
                        .suggestionPlayTime(day.getTravelComponent().getSuggestionPlayTime())
                        .wayOfCommute(day.getTravelComponent().getWayOfCommute())
                        .currentOccupation(day.getTravelComponent().getCurrentOccupation())
                        .futureOccupation(day.getTravelComponent().getFutureOccupation())
                        .isLocation(day.getTravelComponent().getIsLocation())
                        .build())
                .collect(Collectors.toList());

        return TravelRouteListDto.builder()
                .id(travelPlan.getId())
                .title(travelPlan.getTitle())
                .cityName(travelPlan.getCityName())
                .description(travelPlan.getDescription())
                .isLocalTravel(travelPlan.isLocalTravel())
                .isPopular(travelPlan.isPopular())
                .totalTravelDay(highestDay)
                .totalTravelComponent(travelDays.size())
                .route(route)
                .travelComponents(travelLocationEvents)
                .build();
    }
}

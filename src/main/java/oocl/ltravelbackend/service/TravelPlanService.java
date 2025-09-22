package oocl.ltravelbackend.service;

import oocl.ltravelbackend.common.exception.InvalidTravelPlanPaginationInputException;
import oocl.ltravelbackend.model.dto.TravelPlanDetailDTO;
import oocl.ltravelbackend.model.dto.TravelPlanOverviewDto;
import oocl.ltravelbackend.model.entity.TravelDay;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelPlanService {

    @Autowired
    private TravelPlanRepository travelPlanRepository;

    public List<TravelPlanOverviewDto> getPaginatedBasicTravelPlans(int page, int size) {
        if (page < 1 || size <= 0) {
            throw new InvalidTravelPlanPaginationInputException(
                    "Page number must be greater than 0 and page size must be greater than zero");
        }
        List<TravelPlan> result = travelPlanRepository.findTravelPlansByPagination(PageRequest.of(page - 1, size));
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
        //setRoute

        return null;
    }
}

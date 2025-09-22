package oocl.ltravelbackend.model.dto;

import lombok.Builder;
import lombok.Data;
import oocl.ltravelbackend.model.entity.PlanImage;

import java.util.List;

@Data
@Builder
public class TravelPlanOverviewDto {
    private long id;
    private String cityName;
    private String description;
    private int totalTravelDay;
    private int totalTravelComponent;
    private List<PlanImage> travePlanPlanImages;
}

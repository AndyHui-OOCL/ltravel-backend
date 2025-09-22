package oocl.ltravelbackend.model.dto;

import lombok.Builder;
import lombok.Data;
import oocl.ltravelbackend.model.entity.PlanImage;

import java.util.List;

@Data
@Builder
public class BasicTravelPlanRes {
    private int id;
    private String cityName;
    private String description;
    private List<PlanImage> travePlanPlanImages;
    private int totalTravelDay;
    private int totalTravelComponent;
}

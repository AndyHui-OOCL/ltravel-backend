package oocl.ltravelbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oocl.ltravelbackend.model.entity.PlanImage;
import oocl.ltravelbackend.model.entity.TravelComponent;
import oocl.ltravelbackend.model.entity.TravelDay;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelRouteListDto {
    private Long id;
    private String title;
    private String cityName;
    private String description;
    private boolean isLocalTravel;
    private boolean isPopular;
    private int totalTravelDay;
    private int totalTravelComponent;
    private Map<Integer, List<String>> route;
    private List<TravelComponentDto> travelComponents;
}

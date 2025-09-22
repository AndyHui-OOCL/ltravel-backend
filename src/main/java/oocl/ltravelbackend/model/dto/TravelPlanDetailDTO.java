package oocl.ltravelbackend.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TravelPlanDetailDTO {
  private String title;
  private String description;
  private int totalTravelDay;
  private int totalTravelComponent;
  private boolean isLocalTravel;
  private boolean isPopular;

}

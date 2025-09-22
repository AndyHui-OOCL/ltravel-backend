package oocl.ltravelbackend.model.dto;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelPlanDetailDTO {
  private String title;
  private String description;
  private int totalTravelDay;
  private int totalTravelComponent;//总行程数
  private boolean isLocalTravel;
  private boolean isPopular;
  private Map<Integer, List<String>> route;
  private List<String> locationImages;
}

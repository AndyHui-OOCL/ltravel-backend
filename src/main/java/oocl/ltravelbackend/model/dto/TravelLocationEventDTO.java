package oocl.ltravelbackend.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelLocationEventDTO {
  private String eventName;
  private String description;
  private List<String> locationImages;
}

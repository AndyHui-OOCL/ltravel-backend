package oocl.ltravelbackend.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TravelPlanPromptDto {
    private Long id;
    private String title;
    private String cityName;
    private String description;
    private String tag;
}

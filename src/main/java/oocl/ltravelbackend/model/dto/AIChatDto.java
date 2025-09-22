package oocl.ltravelbackend.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AIChatDto {
    private Long travelId;
    private String cityName;
    private String description;
    private String imageUrl;
}

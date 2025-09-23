package oocl.ltravelbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oocl.ltravelbackend.model.entity.ComponentImage;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelComponentDto {
    private Long id;
    private String name;
    private String description;
    private float rating;
    private String openTime;
    private String address;
    private int suggestionPlayTime;
    private String wayOfCommute;
    private String currentOccupation;
    private String futureOccupation;
    private Boolean location;
    private List<ComponentImage> images;
}

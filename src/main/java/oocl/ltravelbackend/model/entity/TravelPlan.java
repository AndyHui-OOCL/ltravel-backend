package oocl.ltravelbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "travel_plan")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String cityName;
    private String description;
    private int totalTravelDay;
    private int totalTravelComponent;
    private boolean isLocalTravel;
    private boolean isPopular;
}

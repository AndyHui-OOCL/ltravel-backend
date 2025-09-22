package oocl.ltravelbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "travel_plan")
@Table(name = "travel_plan")
@AllArgsConstructor
@NoArgsConstructor
public class TravelPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String cityName;
    private String description;
    private int totalTravelDay;
    private int totalTravelComponent;
    private boolean isLocalTravel;
    private boolean isPopular;
}

package oocl.ltravelbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "travel_plan_id")
    private List<Image> images;
}

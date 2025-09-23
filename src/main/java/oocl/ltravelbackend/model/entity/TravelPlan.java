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
    private boolean isLocalTravel;
    private boolean isPopular;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_plan_id")
    private List<TravelDay> travelDays;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_plan_id")
    private List<PlanImage> images;

    @ManyToMany(mappedBy = "savedTravelPlans")
    private List<User> savedByUsers;
}

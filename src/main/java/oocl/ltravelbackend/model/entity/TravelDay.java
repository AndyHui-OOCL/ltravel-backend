package oocl.ltravelbackend.model.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "travel_day")
@AllArgsConstructor
@NoArgsConstructor
public class TravelDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int dayNum;
    @Column(name = "travel_plan_id")
    private Long travelPlanId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_day_id")
    private List<TravelComponent> travelComponent;
}

package oocl.ltravelbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "travel_days")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int dayNum;
    private int componentOrder;

    @Column(name = "travel_plan_id")
    private Long travelPlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_component_id")
    private TravelComponent travelComponent;
}
package oocl.ltravelbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "travel_day")
@Table(name = "travel_day")
@AllArgsConstructor
@NoArgsConstructor
public class TravelDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dayNum;
    private int travelPlanId;
}

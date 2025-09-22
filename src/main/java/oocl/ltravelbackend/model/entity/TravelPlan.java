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
    private String city_name;
    private String description;
    private int total_travel_day;
    private int total_travel_component;
    private boolean is_local_travel;
    private boolean is_popular;
}

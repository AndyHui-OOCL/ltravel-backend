package oocl.ltravelbackend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "travel_plan")
public class TravelPlan {

  @Id
  private int id;
}

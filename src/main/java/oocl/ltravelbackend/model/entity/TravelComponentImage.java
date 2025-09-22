package oocl.ltravelbackend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "travel_component_images")
public class TravelComponentImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int travelComponentId;
  private String imageId;
}

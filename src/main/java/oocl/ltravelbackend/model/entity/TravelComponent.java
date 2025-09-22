package oocl.ltravelbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "travel_components")
public class TravelComponent {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private float rating;
  private int componentOrder;
  private String openTime;
  private String address;
  private int suggestionPlayTime;
  private String wayOfCommute;
  private String currentOccupation;
  private String futureOccupation;
  private Long travelDayId;
  private Long imageId;
  private boolean Location;
}

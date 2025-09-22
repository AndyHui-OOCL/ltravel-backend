package oocl.ltravelbackend.model.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "travel_components")
public class TravelComponent {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private float rating;
  private String openTime;
  private String address;
  private int suggestionPlayTime;
  private String wayOfCommute;
  private String currentOccupation;
  private String futureOccupation;
  private boolean location;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "travel_component_id")
  private List<ComponentImage> images;
}

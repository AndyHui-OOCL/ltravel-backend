package oocl.ltravelbackend.model.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oocl.ltravelbackend.model.entity.TravelComponent;
import oocl.ltravelbackend.model.entity.User;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String description;
    private Boolean isLike;

    private String travelComponentName;

    private String username;
}

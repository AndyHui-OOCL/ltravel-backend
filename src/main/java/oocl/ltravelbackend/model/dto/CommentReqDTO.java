package oocl.ltravelbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentReqDTO {
    private Long travelPlanId;
    private int page;
    private int size;
}

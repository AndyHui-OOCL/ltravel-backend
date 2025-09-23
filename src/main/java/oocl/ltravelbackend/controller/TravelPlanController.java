package oocl.ltravelbackend.controller;

import java.util.List;
import oocl.ltravelbackend.model.dto.TravelPlanDetailDTO;
import oocl.ltravelbackend.model.dto.TravelPlanOverviewDto;
import oocl.ltravelbackend.service.TravelPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import oocl.ltravelbackend.model.entity.TravelComponent;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travel-plans")
public class TravelPlanController {

    @Autowired
    TravelPlanService travelPlanService;

    @GetMapping()
    public ResponseEntity<List<TravelPlanOverviewDto>> getTravelPlanOverview(
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "9") int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelPlanService.getPaginatedBasicTravelPlans(page, size));
    }

  @GetMapping("/detail/{id}")
  public ResponseEntity<TravelPlanDetailDTO> getTravelPlanDetail(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(travelPlanService.getTravelPlanDetailById(id));
  }

}

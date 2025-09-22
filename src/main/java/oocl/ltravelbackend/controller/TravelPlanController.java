package oocl.ltravelbackend.controller;

import oocl.ltravelbackend.model.dto.BasicTravelPlanRes;
import oocl.ltravelbackend.model.dto.TravelPlanDetailDTO;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.service.TravelPlanService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import oocl.ltravelbackend.model.entity.TravelComponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travel-plans")
public class TravelPlanController {

  @Autowired
  TravelPlanService travelPlanService;

  @GetMapping("/details/{id}")
  public TravelComponent getTravelPlanDetails(@PathVariable Long id) {
    return null;
  }

  @GetMapping()
  public ResponseEntity<Page<TravelPlan>> getBasicTravelPlan(
    @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "9") int size) {
    return ResponseEntity.status(HttpStatus.OK)
      .body(travelPlanService.getPaginatedBasicTravelPlans(page, size));
  }


  @GetMapping("/detail/{id}")
  public ResponseEntity<TravelPlanDetailDTO> getTravelPlanDetail(@PathVariable Long id) {
    return travelPlanService.getTravelPlanDetailById(id) != null ?
      ResponseEntity.status(HttpStatus.OK).body(travelPlanService.getTravelPlanDetailById(id)) :
      ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

}

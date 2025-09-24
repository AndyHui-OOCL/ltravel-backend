package oocl.ltravelbackend.controller;

import oocl.ltravelbackend.model.dto.TravelPlanDetailDTO;
import oocl.ltravelbackend.model.dto.TravelPlanOverviewDto;
import oocl.ltravelbackend.service.TravelPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/plan-num")
    public ResponseEntity<Integer> getNumberOfTravelPlans() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelPlanService.getNumOfTravelPlans());
    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<TravelPlanDetailDTO> getTravelPlanDetail(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(travelPlanService.getTravelPlanDetailById(id));
    }

}

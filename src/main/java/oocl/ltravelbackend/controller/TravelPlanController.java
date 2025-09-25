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

    @GetMapping
    public ResponseEntity<List<TravelPlanOverviewDto>> getTravelPlanOverview(
            @RequestParam(required = false, defaultValue = "") String city,
            @RequestParam(required = false) Integer travelDays,
            @RequestParam(required = false, defaultValue = "") String tag,
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "9") int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelPlanService.getFilteredPaginatedTravelPlans(city, travelDays, tag, page, size));
    }

    @GetMapping("/plan-num")
    public ResponseEntity<Integer> getNumberOfTravelPlans(
            @RequestParam(required = false, defaultValue = "") String city,
            @RequestParam(required = false) Integer travelDays,
            @RequestParam(required = false, defaultValue = "") String tag
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelPlanService.getNumOfTravelPlans(city, travelDays, tag));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<TravelPlanDetailDTO> getTravelPlanDetail(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(travelPlanService.getTravelPlanDetailById(id));
    }

}

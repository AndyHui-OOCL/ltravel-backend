package oocl.ltravelbackend.controller;
import oocl.ltravelbackend.model.dto.BasicTravelPlanRes;
import oocl.ltravelbackend.service.TravelPlanService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travel-plans")
public class TravelPlanController {
    @Autowired
    TravelPlanService travelPlanService;

    @GetMapping("/basic")
    public ResponseEntity<BasicTravelPlanRes> getBasicTravelPlan(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "9") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(travelPlanService.getPaginatedBasicTravelPlans(page, size));
    }


}

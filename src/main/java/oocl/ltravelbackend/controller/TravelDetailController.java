package oocl.ltravelbackend.controller;

import oocl.ltravelbackend.model.dto.TravelRouteListDto;
import oocl.ltravelbackend.model.entity.TravelComponent;
import oocl.ltravelbackend.model.entity.TravelDay;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.service.TravelDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travel-detail")
public class TravelDetailController {

    @Autowired
    private TravelDetailService travelDetailService;

    @GetMapping("/plan/{id}")
    public ResponseEntity<TravelPlan> getTravelPlan(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(travelDetailService.getTravelPlan(id));
    }

    @GetMapping("/component/{id}")
    public ResponseEntity<TravelComponent> getTravelComponent(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(travelDetailService.getTravelComponent(id));
    }

    @GetMapping("/day/list/{id}")
    public ResponseEntity<List<TravelDay>> getTravelDayList(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(travelDetailService.getTravelDayList(id));
    }

    @GetMapping("/route/{id}")
    public ResponseEntity<TravelRouteListDto> getTravelPlanDetail(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(travelDetailService.getTravelDayDetailById(id));
    }

}

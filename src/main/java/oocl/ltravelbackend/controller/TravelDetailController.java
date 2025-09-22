package oocl.ltravelbackend.controller;

import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.service.TravelDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travelDetail")
public class TravelDetailController {

    @Autowired
    private TravelDetailService travelDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<TravelPlan> getTravelPlan(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(travelDetailService.getTravelPlan(id));
    }

}

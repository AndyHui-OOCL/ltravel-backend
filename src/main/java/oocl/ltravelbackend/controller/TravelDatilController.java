package oocl.ltravelbackend.controller;

import oocl.ltravelbackend.model.entity.TravelComponent;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.service.TravelDatilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travelDetail")
public class TravelDatilController {

    @Autowired
    private TravelDatilService travelDatilService;

//    @GetMapping
//    public ResponseEntity<TravelPlan> getTravelPlans() {
//
//    }

}

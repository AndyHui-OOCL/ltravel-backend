package oocl.ltravelbackend.controller;

import oocl.ltravelbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/like/{planId}")
    public ResponseEntity<String> likeTravelPlan(@PathVariable Long planId, @RequestParam Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.likeTravelPlan(planId, userId));
    }

}

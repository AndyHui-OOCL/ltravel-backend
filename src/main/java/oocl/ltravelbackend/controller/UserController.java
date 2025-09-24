package oocl.ltravelbackend.controller;

import java.util.List;
import oocl.ltravelbackend.model.dto.TravelPlanOverviewDto;
import oocl.ltravelbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/like/{planId}")
  public ResponseEntity<String> likeTravelPlan(@PathVariable Long planId,@RequestParam Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.likeTravelPlan(planId,userId));
  }

  @PostMapping("/unlike/{planId}")
  public ResponseEntity<String> cancelLikeTravelPlan(@PathVariable Long planId,@RequestParam Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.cancelLikeTravelPlan(planId,userId));
  }

  @GetMapping("/favorite-plans")
  public ResponseEntity<Page<TravelPlanOverviewDto>> getFavoritePlans(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam Long userId) {
    Page<TravelPlanOverviewDto> favoritePlans = userService.getFavoritePlan(page, size, userId);
    return ResponseEntity.ok(favoritePlans);
  }
}

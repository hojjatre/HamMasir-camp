package org.example.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.config.AppConfig;
import org.example.model.Restaurant;
import org.example.schedule.ScheduleTask;
import org.example.service.UserService;
import org.example.view.View;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    Authentication authentication;

    private final Map<String, Integer> codeVerification;

    private List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantController(UserService userService, AuthenticationManager authenticationManager,
                                ScheduleTask scheduleTask, AppConfig appConfig) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        authentication = userService.getAuthentication();
        codeVerification = scheduleTask.getCodeVerification();
        restaurants = appConfig.getRestaurants();
    }


    @GetMapping("/all-restaurant")
    @JsonView(View.publicDetail.class)
    public ResponseEntity<List<Restaurant>> allRestaurant(){
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/restaurant-food")
    @JsonView(View.detailedInfo.class)
    public ResponseEntity<List<Restaurant>> restaurantFood(){
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

}

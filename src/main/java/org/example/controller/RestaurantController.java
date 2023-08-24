package org.example.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.model.Restaurant;
import org.example.model.RestaurantDTO;
import org.example.service.RestaurantService;
import org.example.view.View;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurants){
        this.restaurantService = restaurants;
    }


    @GetMapping("/all-restaurant")
    @JsonView(View.publicDetail.class)
    public ResponseEntity<List<Restaurant>> allRestaurant(){
        return new ResponseEntity<>(restaurantService.getRestaurants(), HttpStatus.OK);
    }

    @GetMapping("/restaurant-food")
    @JsonView(View.detailedInfo.class)
    public ResponseEntity<List<Restaurant>> restaurantFood(){
        return new ResponseEntity<>(restaurantService.getRestaurants(), HttpStatus.OK);
    }

    @PostMapping("/add-restaurant/{codeVerification}")
    public ResponseEntity<Object> addRestaurant(@PathVariable("codeVerification") int code,
                                                @RequestBody RestaurantDTO restaurantDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return restaurantService.addRestaurant(authentication, code, restaurantDTO);
    }

    @PostMapping("/remove-restaurant/{id}/{codeVerification}")
    public ResponseEntity<Object> removeRestaurant(@PathVariable("id") int id,
                                                   @PathVariable("codeVerification") int code){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return restaurantService.removeRestaurant(authentication, id, code);
    }

}

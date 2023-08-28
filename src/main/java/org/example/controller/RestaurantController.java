package org.example.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.model.Food;
import org.example.model.Restaurant;
import org.example.dto.RestaurantDTO;
import org.example.service.RestaurantService;
import org.example.view.View;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
//    @JsonView(View.publicDetail.class)
    public ResponseEntity<List<Restaurant>> allRestaurant(){
        return new ResponseEntity<>(restaurantService.getRestaurants(), HttpStatus.OK);
    }

    @GetMapping("/restaurant-food")
    @JsonView(View.detailedInfo.class)
    public ResponseEntity<List<Restaurant>> restaurantFood(){
        return new ResponseEntity<>(restaurantService.getRestaurants(), HttpStatus.OK);
    }

    @PostMapping("/add-restaurant")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Object> addRestaurant(@RequestParam int code,
                                                @RequestBody RestaurantDTO restaurantDTO){
        System.out.println("------------");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return restaurantService.addRestaurant(authentication, code, restaurantDTO);
    }

    @PostMapping("/remove-restaurant/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Object> removeRestaurant(@PathVariable("id") int id,
                                                   @RequestParam int code){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return restaurantService.removeRestaurant(authentication, id, code);
    }

    @PostMapping("/change-cost-food/{foodID}/{restaurantID}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Object> changeCostFood(@PathVariable("foodID") int foodID,
                                             @PathVariable("restaurantID") int restaurantID,
                                             @RequestParam int code,
                                             @RequestParam Integer inputCost){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return restaurantService.changeCostFood(authentication, restaurantID, foodID, code, inputCost);
    }

    @PostMapping("/remove-food/{foodID}/{restaurantID}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Object> removeFood(@PathVariable("foodID") int foodID,
                                             @PathVariable("restaurantID") int restaurantID,
                                             @RequestParam int code){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return restaurantService.removeFood(authentication, restaurantID, foodID, code);
    }

    @PostMapping("/add-food/{restaurantID}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Object> addFood(@PathVariable("restaurantID") int restaurantID,
                                          @RequestParam int code,
                                          @RequestBody Food food,
                                          @RequestParam Integer inputCost){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return restaurantService.addFood(authentication, restaurantID, food, code, inputCost);
    }

}

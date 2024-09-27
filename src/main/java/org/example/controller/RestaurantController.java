package org.example.controller;

import org.example.cachemanager.RestaurantCache;
import org.example.config.RedisConfig;
import org.example.dto.FoodView;
import org.example.dto.restaurant.RestaurantDTOredis;
import org.example.dto.restaurant.RestaurantView;
import org.example.model.Food;
import org.example.dto.restaurant.RestaurantDTO;
import org.example.model.Restaurant;
import org.example.repository.FoodRepository;
import org.example.repository.RestaurantRepository;
import org.example.repository.UserRepository;
import org.example.service.RestaurantService;
import org.redisson.api.RMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    private final RestaurantCache restaurantCache;

    private final RedisConfig redisConfig;

    public RestaurantController(RestaurantService restaurants, RestaurantRepository restaurantRepository, FoodRepository foodRepository, UserRepository userRepository, RestaurantCache restaurantCache, RedisConfig redisConfig){
        this.restaurantService = restaurants;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
        this.restaurantCache = restaurantCache;
        this.redisConfig = redisConfig;
    }


    @GetMapping("/all-restaurant")
    public ResponseEntity<Object> allRestaurant(){
        restaurantCache.loadDataToCache();
        System.out.println("Data into Cache");
        return new ResponseEntity<>(restaurantRepository.findAllRestaurant(), HttpStatus.OK);
    }

    @GetMapping("/restaurant-food")
    public ResponseEntity<List<FoodView>> restaurantFood(){
        return ResponseEntity.ok(foodRepository.allFoods());
    }


    @PostMapping("/add-restaurant")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Object> addRestaurant(@RequestParam int code,
                                                @RequestBody RestaurantDTO restaurantDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return restaurantService.addRestaurant(authentication, code, restaurantDTO);
    }

    @PostMapping("/remove-restaurant/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Object> removeRestaurant(@PathVariable("id") Long id,
                                                   @RequestParam int code){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return restaurantService.removeRestaurant(authentication, id, code);
    }

    @PostMapping("/change-cost-food/{foodID}/{restaurantID}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Object> changeCostFood(@PathVariable("foodID") Long foodID,
                                             @PathVariable("restaurantID") Long restaurantID,
                                             @RequestParam int code,
                                             @RequestParam Integer inputCost){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return restaurantService.changeCostFood(authentication, restaurantID, foodID, code, inputCost);
    }

    @PostMapping("/remove-food/{foodID}/{restaurantID}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Object> removeFood(@PathVariable("foodID") Long foodID,
                                             @PathVariable("restaurantID") Long restaurantID,
                                             @RequestParam int code){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return restaurantService.removeFood(authentication, restaurantID, foodID, code);
    }

    @PostMapping("/add-food/{restaurantID}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Object> addFood(@PathVariable("restaurantID") Long restaurantID,
                                          @RequestParam int code,
                                          @RequestBody Food food){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return restaurantService.addFood(authentication, restaurantID, food, code);
    }

    @GetMapping("/most-expensive-food")
    public ResponseEntity<RestaurantView> getRestaurantWithMostExpensiveFood(){
        return ResponseEntity.ok(restaurantRepository.findTopByOrderByFoodsCostDesc());
    }

    @GetMapping("/by-food-description")
    public ResponseEntity<List<RestaurantView>> getRestaurantsByFoodDescription(@RequestParam String description) {
        List<RestaurantView> customResponses = restaurantRepository.findByFoodsDescriptionContaining(description);
        return ResponseEntity.ok(customResponses);
    }

}

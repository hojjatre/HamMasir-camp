package org.example.controller;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.example.config.AppConfig;
import org.example.model.*;
import org.example.schedule.ScheduleTask;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    Authentication authentication;

    private final Map<String, Integer> codeVerification;

    private List<Restaurant> restaurants = new ArrayList<>();

    private List<Food> foods;

    public OrderController(UserService userService, AuthenticationManager authenticationManager,
                           ScheduleTask scheduleTask, AppConfig appConfig) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        authentication = userService.getAuthentication();
        codeVerification = scheduleTask.getCodeVerification();
        restaurants = appConfig.getRestaurants();
        foods = appConfig.getFoods();
    }

    @PostMapping("/make-order/{id}")
    public ResponseEntity<Object> makeOrder(@PathVariable("id") int id, @RequestBody MakeOrderDTO makeOrderDTO){
        if (restaurants.get(id) == null){
            return new ResponseEntity("The restaurant is not exist. try again...", HttpStatus.NOT_FOUND);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserImp userImp = userService.getUserImps().get(authentication.getName());
        Restaurant restaurant = restaurants.get(id);
        ListMultimap<Food, Integer> costs = ArrayListMultimap.create();
        int totalCost = 0;
        // TODO : make a method for the code below
        if (userImp != null){
            for (Integer foodID: makeOrderDTO.getFoodID()) {
                for (Food food:restaurant.getCost().keySet()) {
                    if (food.getId() == foodID){
                        costs.put(food, restaurant.getCost().get(food));
                        totalCost = totalCost + restaurant.getCost().get(food);
                    }
                }
            }
        }
        else {
            return new ResponseEntity("Your verification code is not VALID.", HttpStatus.OK);
        }

        Map<Food, List<Integer>> resultMap = new HashMap<>();

        for (Food key : costs.keySet()) {
            resultMap.put(key, costs.get(key));
        }
        Order order = new Order(restaurant, resultMap, "همراه با قاشق یکبار مصرف");
        order.setTotalCost(totalCost);
        userImp.setOrders(Collections.singletonList(order));

        return new ResponseEntity(userImp, HttpStatus.OK);
    }

}

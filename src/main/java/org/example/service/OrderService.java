package org.example.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.example.config.AppConfig;
import org.example.model.Food;
import org.example.model.Order;
import org.example.model.Restaurant;
import org.example.model.UserImp;
import org.example.schedule.ScheduleTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    Authentication authentication;

    private final Map<String, Integer> codeVerification;

    private List<Restaurant> restaurants = new ArrayList<>();

    private List<Food> foods;

    public OrderService(UserService userService, AuthenticationManager authenticationManager,
                           ScheduleTask scheduleTask, AppConfig appConfig) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        authentication = userService.getAuthentication();
        codeVerification = scheduleTask.getCodeVerification();
        restaurants = appConfig.getRestaurants();
        foods = appConfig.getFoods();
    }

    public ResponseEntity<Object> addOrderByUser(Authentication authentication, int id, Integer[] food_ID){
        try {
            UserImp userImp = userService.getUserImps().get(authentication.getName());
            Restaurant restaurant = restaurants.get(id);
            ListMultimap<Food, Integer> costs = ArrayListMultimap.create();
            int totalCost = 0;
            if (userImp != null){
                for (Integer foodID: food_ID) {
                    for (Food food:restaurant.getCost().keySet()) {
                        if (food.getId() == foodID){
                            costs.put(food, restaurant.getCost().get(food));
                            totalCost = totalCost + restaurant.getCost().get(food);
                        }
                    }
                }
            }
            else {
                new ResponseEntity<Object>("User not find, login.",HttpStatus.NOT_FOUND);
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
        catch (Exception e){
            return new ResponseEntity("The restaurant is not exist. try again...", HttpStatus.NOT_FOUND);
        }

    }
}

package org.example.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import jakarta.transaction.Transactional;
import org.example.model.Food;
import org.example.model.Order;
import org.example.model.Restaurant;
import org.example.model.UserImp;
import org.example.repository.OrderRepository;
import org.example.repository.RestaurantRepository;
import org.example.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;



    public OrderService(UserRepository userRepository, OrderRepository orderRepository,
                        RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
    }
    @Transactional
    public ResponseEntity<Object> addOrderByUser(Authentication authentication, int id, Integer[] food_ID){
        try {
            UserImp userImp = userRepository.findByUsername(authentication.getName());
            Restaurant restaurant = restaurantRepository.findByRestaurantID(id);
            Order order = new Order(restaurant, "همراه با قاشق یکبار مصرف");
            ListMultimap<Food, Integer> costs = ArrayListMultimap.create();
            int totalCost = 0;
            if (userImp != null){
                System.out.println("Not null");
                restaurant.getFoods().stream().forEach(fo -> System.out.println(fo.getFoodID() + ", " + fo.getName()));
                for (Integer foodID: food_ID) {
                    System.out.println(foodID);
                    for (Food food:restaurant.getFoods()) {
                        System.out.println("--- " + food.getFoodID());
                        if (food.getFoodID() == foodID){
                            System.out.println(foodID + ", " + food.getCost());
                            costs.put(food, food.getCost());
                            totalCost = totalCost + food.getCost();
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


            order.setTotalCost(totalCost);
            order.setFood(resultMap.keySet().stream().toList());
            orderRepository.save(order);
            userImp.getOrders().add(order);
            userRepository.save(userImp);

            return new ResponseEntity<>("Done.", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("The restaurant is not exist. try again...", HttpStatus.NOT_FOUND);
        }

    }
}

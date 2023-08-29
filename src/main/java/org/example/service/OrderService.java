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
    public ResponseEntity<Object> addOrderByUser(Authentication authentication, Long id, Long[] food_ID){
        try {
            UserImp userImp = userRepository.findByUsername(authentication.getName());
            Restaurant restaurant = restaurantRepository.findByRestaurantID(id);
            Order order = new Order(restaurant, "همراه با قاشق یکبار مصرف");
            List<Food> inputFoods = new ArrayList<>();
            int totalCost = 0;
            if (userImp != null){
                for (Long foodID: food_ID) {
                    for (Food food:restaurant.getFoods()) {
                        if (food.getFoodID() == foodID){
                            inputFoods.add(food);
                            totalCost = totalCost + food.getCost();
                        }
                    }
                }
            }
            else {
                new ResponseEntity<Object>("User not find, login.",HttpStatus.NOT_FOUND);
            }



            order.setTotalCost(totalCost);
            order.setFood(inputFoods);
            order.setUser_order(userImp);
            userImp.getOrders().add(order);
            userRepository.save(userImp);

            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("The restaurant is not exist. try again...", HttpStatus.NOT_FOUND);
        }

    }
}

package org.example.service;

//import jakarta.transaction.Transactional;
import jakarta.persistence.criteria.Predicate;
import org.example.dto.search.CustomSearchOrderResponse;
import org.example.dto.search.OrderSearchCriteria;
import org.example.dto.OrderView;
import org.example.model.Food;
import org.example.model.Order;
import org.example.model.Restaurant;
import org.example.model.UserImp;
import org.example.repository.OrderRepository;
import org.example.repository.RestaurantRepository;
import org.example.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;

    private final ModelMapper modelMapper;



    public OrderService(UserRepository userRepository, OrderRepository orderRepository,
                        RestaurantRepository restaurantRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.modelMapper = modelMapper;
    }
    @Transactional
    public ResponseEntity<Object> addOrderByUser(Authentication authentication, Long id, Long[] food_ID){
        try {
            UserImp userImp = userRepository.findByUsername(authentication.getName());
            Restaurant restaurant = restaurantRepository.findByRestaurantID(id, Restaurant.class);
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
            order.setUser_order(userImp);
            userImp.getOrders().add(order);
            orderRepository.save(order);
            OrderView orderView = orderRepository.findByOrderID(order.getOrderID());


            return new ResponseEntity<>(orderView, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("The restaurant is not exist. try again...", HttpStatus.NOT_FOUND);
        }

    }

    public List<CustomSearchOrderResponse> searchOrders(OrderSearchCriteria criteria) {
        List<Order> orders = orderRepository.findAll(buildQuery(criteria));
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private Specification<Order> buildQuery(OrderSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getUsername() != null) {
                predicates.add(criteriaBuilder.equal(root.get("user_order").get("username"), criteria.getUsername()));
            }

            if (criteria.getRestaurantId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("restaurant").get("id"), criteria.getRestaurantId()));
            }

            if (criteria.getTotalCost() != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("totalCost"), criteria.getTotalCost()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private CustomSearchOrderResponse convertToDto(Order order) {
        return modelMapper.map(order, CustomSearchOrderResponse.class);
    }

}

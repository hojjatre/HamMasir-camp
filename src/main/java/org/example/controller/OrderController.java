package org.example.controller;

import org.example.dto.*;
import org.example.dto.search.CustomSearchOrderResponse;
import org.example.dto.search.OrderSearchCriteria;
import org.example.dto.userimp.UserView;
import org.example.repository.OrderRepository;
import org.example.repository.UserRepository;
import org.example.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    public OrderController(OrderService orderService, OrderRepository orderRepository, UserRepository userRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/make-order/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<Object> makeOrder(@PathVariable("id") Long id, @RequestBody MakeOrderDTO makeOrderDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return orderService.addOrderByUser(authentication, id, makeOrderDTO.getFoodID());
    }

    @PostMapping("/most-total-cost")
    public ResponseEntity<List<UserView>> mostTotalCost(@RequestParam("thresholdAmount") int thresholdAmount){
        List<UserView> userViews = userRepository.findUsersWithTotalSumCostExceedingThreshold(thresholdAmount);
        return ResponseEntity.ok(userViews);
    }

    @GetMapping("/count-order-restaurant")
    public ResponseEntity<List<OrderCountDTO>> countOrdersByUserAndRestaurant() {
        List<OrderCountDTO> orderCounts = orderRepository.countOrdersByUserAndRestaurant();
        return ResponseEntity.ok(orderCounts);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomSearchOrderResponse>> searchOrders(@ModelAttribute OrderSearchCriteria criteria) {
        System.out.println(criteria.getUsername() + ", " + criteria.getTotalCost() + ", " + criteria.getRestaurantId());
        List<CustomSearchOrderResponse> orders = orderService.searchOrders(criteria);
        return ResponseEntity.ok(orders);
    }
}

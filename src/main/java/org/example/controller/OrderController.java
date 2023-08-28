package org.example.controller;

import org.example.dto.MakeOrderDTO;
import org.example.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/make-order/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<Object> makeOrder(@PathVariable("id") int id, @RequestBody MakeOrderDTO makeOrderDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return orderService.addOrderByUser(authentication, id, makeOrderDTO.getFoodID());
    }

}

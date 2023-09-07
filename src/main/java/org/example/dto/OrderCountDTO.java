package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Restaurant;
import org.example.model.UserImp;


public class OrderCountDTO {

    private Long orderCount;

    private String username;
    private Long restaurantID;
    private String restaurantName;

    public OrderCountDTO(UserImp user, Restaurant restaurant, Long orderCount) {
        this.username =  user.getUsername();
        this.restaurantID = restaurant.getRestaurantID();
        this.restaurantName = restaurant.getName();
        this.orderCount = orderCount;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Long restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}


package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Restaurant;
import org.example.model.UserImp;

@Getter
@Setter
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

}


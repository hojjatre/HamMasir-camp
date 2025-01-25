package org.example.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



public class RestaurantDTOredis {
    private Long restaurantID;
    private String name;
    private String location;

    public RestaurantDTOredis(Long restaurantID, String name, String location) {
        this.restaurantID = restaurantID;
        this.name = name;
        this.location = location;
    }

    public RestaurantDTOredis() {
    }

    public Long getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Long restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

package org.example.dto.userimp;

import org.example.dto.restaurant.FoodRestaurantView;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserOrderView {

    @Value("#{target.restaurant.name}")
    String getRestaurantName();

    @Value("#{target.food}")
    List<FoodRestaurantView> getListFood();

    @Value("#{target.totalCost}")
    String getTotalCost();

}

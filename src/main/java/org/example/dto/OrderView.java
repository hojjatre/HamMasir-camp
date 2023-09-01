package org.example.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.dto.restaurant.FoodRestaurantView;
import org.example.model.Food;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface OrderView {

    @Value("#{target.user_order.username}")
    String getUserName();

    @Value("#{target.restaurant.name}")
    String getRestaurantName();

    @Value("#{target.restaurant.location}")
    String getRestaurantLocation();

//    @JsonIgnore
    @Value("#{target.food}")
    List<FoodRestaurantView> getListFood();

//    public default String getFoods(){
//        List<Food> foods = getListFood();
//        String out = "";
//        for (Food food:foods) {
//            out = out + food.getName() + " :" + food.getCost();
//        }
//        return out;
//    }

    @Value("#{target.totalCost}")
    String getTotalCost();

    @Value("#{target.description}")
    String getDescription();
}

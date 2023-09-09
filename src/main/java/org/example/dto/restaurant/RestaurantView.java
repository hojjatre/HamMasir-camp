package org.example.dto.restaurant;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface RestaurantView {
    @Value("#{target.name}")
    String getName();

    @Value("#{target.owner.username}")
    String getOwnerName();

    @Value("#{target.location}")
    String getLocation();

//    @JsonIgnore
    @Value("#{target.foods}")
    List<FoodRestaurantView> getFoods();

//    default String getFood(){
//        List<Food> foods = getFoods();
//        String result = "";
//        for (Food food:foods) {
//            result = result + food.getName() + ", " + food.getCost() + " - ";
//        }
//
//        return result;
//    }
}

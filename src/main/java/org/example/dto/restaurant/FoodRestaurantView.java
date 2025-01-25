package org.example.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;


public interface FoodRestaurantView {

    @JsonIgnore
    @Value("#{target.foodID}")
    Long getFoodID();
    @Value("#{target.name}")
    String getName();

    @Value("#{target.typeFood}")
    String getTypeFood();

    @Value("#{target.cost}")
    int getCost();

    @Value("#{target.description}")
    String getDescription();

}

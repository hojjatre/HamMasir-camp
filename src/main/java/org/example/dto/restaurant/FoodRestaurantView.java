package org.example.dto.restaurant;

import org.springframework.beans.factory.annotation.Value;


public interface FoodRestaurantView {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.typeFood}")
    String getTypeFood();

    @Value("#{target.cost}")
    int getCost();

    @Value("#{target.description}")
    String getDescription();

}

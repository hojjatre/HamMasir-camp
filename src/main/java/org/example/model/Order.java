package org.example.model;

import com.google.common.collect.ListMultimap;

import java.util.List;
import java.util.Map;

public class Order {
    private static int id = 0;
    private int orderID;
    private Restaurant restaurant;
    private Map<Food, List<Integer>> foodCost;
    private Integer totalCost;
    private String description;

    public Order(Restaurant restaurant, Map<Food, List<Integer>> foodCost, String description) {
        this.restaurant = restaurant;
        this.foodCost = foodCost;
        this.description = description;
        orderID = id;
        id = id + 1;
    }

    public int getId() {
        return orderID;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Map<Food, List<Integer>> getFoodCost() {
        return foodCost;
    }

    public void setFoodCost(Map<Food, List<Integer>> foodCost) {
        this.foodCost = foodCost;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

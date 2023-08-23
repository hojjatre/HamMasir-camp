package org.example.model;

import java.util.Map;

public class Order {
    private static int id = -1;
    private Restaurant restaurant;
    private Map<Food, Double> foodCost;
    private double totalCost;
    private String description;

    public Order(Restaurant restaurant, Map<Food, Double> foodCost, String description) {
        this.restaurant = restaurant;
        this.foodCost = foodCost;
        this.description = description;
        id = id + 1;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Order.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Map<Food, Double> getFoodCost() {
        return foodCost;
    }

    public void setFoodCost(Map<Food, Double> foodCost) {
        this.foodCost = foodCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

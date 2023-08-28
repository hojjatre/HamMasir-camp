package org.example.dto;

import java.util.Map;

public class RestaurantDTO {
    private String name;
    private String location;

    private String[] nameFood;
    private String[] typeFood;
    private String[] description;
    private Integer[] cost;

    public RestaurantDTO(String name, String location, String[] nameFood, String[] typeFood, String[] description, Integer[] cost) {
        this.name = name;
        this.location = location;
        this.nameFood = nameFood;
        this.typeFood = typeFood;
        this.description = description;
        this.cost = cost;
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

    public String[] getNameFood() {
        return nameFood;
    }

    public void setNameFood(String[] nameFood) {
        this.nameFood = nameFood;
    }

    public String[] getTypeFood() {
        return typeFood;
    }

    public void setTypeFood(String[] typeFood) {
        this.typeFood = typeFood;
    }

    public String[] getDescription() {
        return description;
    }

    public void setDescription(String[] description) {
        this.description = description;
    }

    public Integer[] getCost() {
        return cost;
    }

    public void setCost(Integer[] cost) {
        this.cost = cost;
    }
}

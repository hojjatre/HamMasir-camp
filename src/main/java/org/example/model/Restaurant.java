package org.example.model;

import java.util.Map;

public class Restaurant {
    private String name;
    private User owner;
    private String location;
    private Map<Food, Integer> cost;

    public Restaurant(String name, User owner, String location, Map<Food, Integer> cost) {
        this.name = name;
        this.owner = owner;
        this.location = location;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map<Food, Integer> getCost() {
        return cost;
    }

    public void setCost(Map<Food, Integer> cost) {
        this.cost = cost;
    }

}

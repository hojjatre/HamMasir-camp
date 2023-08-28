package org.example.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.view.View;

import java.util.Map;

public class Restaurant {

    private static int id = 0;
    @JsonView(View.publicDetail.class)
    private int restaurantID;
    @JsonView({View.publicDetail.class, View.detailedInfo.class})
    private String name;
    @JsonView(View.privateDetail.class)
    private UserImp owner;
    @JsonView({View.publicDetail.class, View.detailedInfo.class})
    private String location;
    @JsonView(View.detailedInfo.class)
    private Map<Food, Integer> cost;

    public Restaurant(String name, UserImp owner, String location, Map<Food, Integer> cost) {
        restaurantID = id;
        id = id + 1;
        this.name = name;
        this.owner = owner;
        this.location = location;
        this.cost = cost;
    }

    public int getId() {
        return restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserImp getOwner() {
        return owner;
    }

    public void setOwner(UserImp owner) {
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

//    public void changeFoodCost(Food food, Integer inputCost){
//        Map<Food, Integer> temp = this.cost;
//        temp.put(food, inputCost);
//        this.cost = temp;
//    }

}

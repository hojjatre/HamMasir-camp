package org.example.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.view.View;

import java.util.List;
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
    private List<Food> foods;

    public Restaurant(String name, UserImp owner, String location, List<Food> foods) {
        restaurantID = id;
        id = id + 1;
        this.name = name;
        this.owner = owner;
        this.location = location;
        this.foods = foods;
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

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public void addFood(Food food){
        this.foods.add(food);
    }

    public void removeFood(Food food){
        this.foods.remove(food);
    }
    //    public void changeFoodCost(Food food, Integer inputCost){
//        Map<Food, Integer> temp = this.cost;
//        temp.put(food, inputCost);
//        this.cost = temp;
//    }

}

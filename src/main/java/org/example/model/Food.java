package org.example.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.view.View;

public class Food {

    private static int id = 0;
    private int foodID;
    @JsonView(View.detailedInfo.class)
    private String name;
    @JsonView(View.detailedInfo.class)
    private TypeFood typeFood;
    @JsonView(View.detailedInfo.class)
    private int cost;

    private String description;

    public Food(String name, TypeFood typeFood, String description) {
        foodID = id;
        id = id + 1;
        this.name = name;
        this.typeFood = typeFood;
        this.description = description;
    }
    public Food(){
        foodID = id;
        id = id + 1;
    }

    public int getId() {
        return foodID;
    }

    public Food(String name, int cost, TypeFood typeFood, String description) {
        foodID = id;
        id = id + 1;
        this.name = name;
        this.cost = cost;
        this.typeFood = typeFood;
        this.description = description;
    }


    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeFood getTypeFood() {
        return typeFood;
    }

    public void setTypeFood(TypeFood typeFood) {
        this.typeFood = typeFood;
    }

    @Override
    public String toString() {
        return this.getId() + " - " +name + " - " + typeFood.getTypeFood();
    }
}

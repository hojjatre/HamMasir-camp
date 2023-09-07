package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.TypeFood;

public class FoodDTOredis {
    private Long foodID;
    private String name;
    private TypeFood typeFood;
    private int cost;
    private String description;

    public FoodDTOredis(Long foodID, String name, TypeFood typeFood, int cost, String description) {
        this.foodID = foodID;
        this.name = name;
        this.typeFood = typeFood;
        this.cost = cost;
        this.description = description;
    }

    public FoodDTOredis() {
    }

    public Long getFoodID() {
        return foodID;
    }

    public void setFoodID(Long foodID) {
        this.foodID = foodID;
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
}

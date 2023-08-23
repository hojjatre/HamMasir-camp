package org.example.model;

public class MakeOrderDTO {
    private Integer[] foodID;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MakeOrderDTO(Integer[] foodID, String description) {
        this.foodID = foodID;
        this.description = description;
    }

    public Integer[] getFoodID() {
        return foodID;
    }

    public void setFoodID(Integer[] foodID) {
        this.foodID = foodID;
    }
}

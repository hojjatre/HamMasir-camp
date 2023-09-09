package org.example.dto;

public class MakeOrderDTO {
    private Long[] foodID;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MakeOrderDTO(Long[] foodID, String description) {
        this.foodID = foodID;
        this.description = description;
    }

    public Long[] getFoodID() {
        return foodID;
    }

    public void setFoodID(Long[] foodID) {
        this.foodID = foodID;
    }
}

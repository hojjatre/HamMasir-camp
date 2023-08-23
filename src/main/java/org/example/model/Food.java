package org.example.model;

public class Food {
    private String name;
    private TypeFood typeFood;

    private String description;

    public Food(String name, TypeFood typeFood, String description) {
        this.name = name;
        this.typeFood = typeFood;
        this.description = description;
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
        return "اسم غذا:" + name + " و نوع غذا:" + typeFood.getTypeFood() + ".";
    }
}

package org.example.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.view.View;

@Entity
@Table(name = "food")
@Getter
@Setter
@NoArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long foodID;

    private String name;

    @Enumerated(EnumType.STRING)
    private TypeFood typeFood;


    private int cost;

    private String description;


    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Food(String name, TypeFood typeFood, int cost, String description) {
        this.name = name;
        this.typeFood = typeFood;
        this.cost = cost;
        this.description = description;
    }


    public Food(String name, int cost, TypeFood typeFood, String description) {
        this.name = name;
        this.cost = cost;
        this.typeFood = typeFood;
        this.description = description;
    }



    @Override
    public String toString() {
        return this.getFoodID() + " - " +name + " - " + typeFood.getTypeFood();
    }
}

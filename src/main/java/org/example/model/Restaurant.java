package org.example.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.view.View;

import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantID;


    private String name;

    @JsonView(View.privateDetail.class)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserImp owner;

    private String location;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Food> foods;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "restaurant_id")
    private List<Order> order;

    public Restaurant(String name,UserImp owner, String location, List<Food> foods) {
        this.name = name;
        this.owner = owner;
        this.location = location;
        this.foods = foods;
    }


    public void addFood(Food food){
        this.foods.add(food);
    }

    public void removeFood(Food food){
        this.foods.remove(food);
    }

}

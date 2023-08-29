package org.example.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.publicDetail.class)
    @Column(name = "restaurant_id")
    private int restaurantID;
    @JsonView({View.publicDetail.class, View.detailedInfo.class})
    private String name;

//    @Column(name = "user_id")
//    private int user_id;

    @JsonView(View.privateDetail.class)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserImp owner;

    @JsonView({View.publicDetail.class, View.detailedInfo.class})
    private String location;
    @JsonView(View.detailedInfo.class)

//    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private List<Food> foods;

//    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.REMOVE)
    @OneToMany(cascade = CascadeType.REMOVE)
//    @JoinTable(name = "restaurant_order",
//            joinColumns =
//                    { @JoinColumn(name = "restaurant_id", referencedColumnName = "order_id") },
//            inverseJoinColumns =
//                    { @JoinColumn(name = "order_id", referencedColumnName = "restaurant_id") })
//    @JoinColumn(name = "order_id", referencedColumnName = "restaurant_id")
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

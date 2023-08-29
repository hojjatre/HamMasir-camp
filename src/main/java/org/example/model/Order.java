package org.example.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.ListMultimap;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.view.View;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    @JsonView(View.addOrder.class)
    private Long orderID;


    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonView(View.addOrder.class)
    private Restaurant restaurant;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserImp user_order;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_food",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "food_id")}
    )
    @JsonView(View.addOrder.class)
    private List<Food> food;


    @JsonView(View.addOrder.class)
    private Integer totalCost;

    @JsonView(View.addOrder.class)
    private String description;

    public Order(Restaurant restaurant, String description) {
        this.restaurant = restaurant;
        this.description = description;
    }



}

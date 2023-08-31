package org.example.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.ListMultimap;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.view.View;

import java.util.Date;
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
    private Long orderID;


    @ManyToOne
    @JoinColumn(name = "restaurant_id")
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
    private List<Food> food;


    private Integer totalCost;

    private String description;

    private Date date;

    public Order(Restaurant restaurant, String description) {
        this.restaurant = restaurant;
        this.description = description;
        this.date = new Date();
    }



}

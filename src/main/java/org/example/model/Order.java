package org.example.model;

import com.google.common.collect.ListMultimap;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private int orderID;

//    @OneToOne(mappedBy = "order")
//    @JoinColumn(name = "restaurant_id", referencedColumnName = "order_id")
//    @JoinColumn(name = "restaurant_id")
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

//    @Column(name = "user_id")
//    private int user_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserImp user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_food",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "food_id")}
    )
    private List<Food> food;

//    private Map<Food, List<Integer>> foodCost;
    private Integer totalCost;
    private String description;

    public Order(Restaurant restaurant, String description) {
        this.restaurant = restaurant;
        this.description = description;
    }



}

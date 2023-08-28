package org.example.model;

import java.util.List;
import java.util.Set;

public class UserImp {
    private static Long id = 0L;
    private Long userID;
    private String username;
    private String email;
    private String password;

    private Set<Role> roles;

    private List<Order> orders;


    public UserImp(String username, String email, String password) {
        userID = id;
        id = id + 1;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserImp(){}

    public Long getId() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order){
        orders.add(order);
    }
}

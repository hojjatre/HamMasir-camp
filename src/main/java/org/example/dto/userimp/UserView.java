package org.example.dto.userimp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.model.Order;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserView {

    @Value("#{target.username}")
    String getUserName();

    @Value("#{target.email}")
    String getEmail();

//    @JsonIgnore
    @Value("#{target.orders}")
    List<UserOrderView> getOrders();

//    default int getTotalCost(){
//        List<Order> orders = getOrders();
//        int totalCost = 0;
//        for (Order order: orders) {
//            totalCost = totalCost + order.getTotalCost();
//        }
//        return totalCost;
//    }

}

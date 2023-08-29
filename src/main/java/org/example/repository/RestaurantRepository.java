package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.example.model.Food;
import org.example.model.Restaurant;
import org.example.model.UserImp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByRestaurantIDAndOwner(int restaurantID, UserImp owner);

    Restaurant findByRestaurantID(int id);


}

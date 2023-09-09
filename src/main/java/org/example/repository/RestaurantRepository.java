package org.example.repository;

import org.example.dto.restaurant.RestaurantView;
import org.example.model.Restaurant;
import org.example.model.UserImp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByRestaurantIDAndOwner(Long restaurantID, UserImp owner);

    <T> T findByRestaurantID(Long id, Class<T> type);


    @Query("select r from Restaurant r")
    List<RestaurantView> findAllRestaurant();


    @Query("select r from Restaurant r where r.restaurantID = :id")
    RestaurantView findRestaurant(@Param("id") Long id);

    RestaurantView findTopByOrderByFoodsCostDesc();

    List<RestaurantView> findByFoodsDescriptionContaining(String description);

}

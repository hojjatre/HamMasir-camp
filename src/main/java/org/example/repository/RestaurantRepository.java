package org.example.repository;

import org.example.dto.restaurant.RestaurantDTOredis;
import org.example.dto.restaurant.RestaurantView;
import org.example.model.Restaurant;
import org.example.model.UserImp;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByRestaurantIDAndOwner(Long restaurantID, UserImp owner);

    <T> T findByRestaurantID(Long id, Class<T> type);

    @Query("SELECT NEW org.example.dto.restaurant.RestaurantDTOredis(r.restaurantID, r.name, r.location) from Restaurant r where r.restaurantID = :id")
    RestaurantDTOredis findRestaurantForRedis(@Param("id") Long id);

    @Query("select r from Restaurant r")
    List<RestaurantDTOredis> findAllRestaurantRedis();

    @EntityGraph(value = "graph.restaurant", type = EntityGraph.EntityGraphType.FETCH)
    @Query("select r from Restaurant r")
    List<RestaurantView> findAllRestaurant();



    @Query("select r from Restaurant r where r.restaurantID = :id")
    RestaurantView findRestaurant(@Param("id") Long id);

    RestaurantView findTopByOrderByFoodsCostDesc();

    List<RestaurantView> findByFoodsDescriptionContaining(String description);

}

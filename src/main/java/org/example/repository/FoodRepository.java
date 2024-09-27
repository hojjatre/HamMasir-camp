package org.example.repository;

import org.example.dto.FoodDTOredis;
import org.example.dto.FoodView;
import org.example.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {
    Food findByFoodID(Long foodID);

    @Query("select f from Food f")
    List<FoodView> allFoods();

    @Query("select f from Food f")
    List<FoodDTOredis> allFoodRedis();

    @Query("SELECT new org.example.dto.FoodDTOredis(f.foodID, f.name, f.typeFood, f.cost, f.description) from Food f where f.foodID = :id")
    FoodDTOredis findFoodByFoodIDredis(@Param("id") Long id);

    @Modifying
    @Query("update Food set cost = :inputCost where foodID = :id")
    void changeCostFood(@Param("id") int id,@Param("inputCost") int cost);

    void removeFoodByFoodID(int id);

}

package org.example.repository;

import org.example.dto.FoodView;
import org.example.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {
    Food findByFoodID(Long foodID);

    @Query("select f from Food f")
    List<FoodView> allFoods();

    @Modifying
    @Query("update Food set cost = :inputCost where foodID = :id")
    void changeCostFood(@Param("id") int id,@Param("inputCost") int cost);

    void removeFoodByFoodID(int id);

}

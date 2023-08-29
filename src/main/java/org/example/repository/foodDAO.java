//package org.example.repository;
//
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import org.example.model.Food;
//import org.example.model.Restaurant;
//
//import java.util.List;
//
//public class foodDAO {
//    private EntityManager entityManager;
//
//    @Transactional
//    public void removeFood(int food_id){
//        Food food = entityManager.find(Food.class, food_id);
//        entityManager.remove(food);
//    }
//}

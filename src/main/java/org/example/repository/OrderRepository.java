package org.example.repository;

import org.example.dto.OrderCountDTO;
import org.example.dto.OrderView;
import org.example.model.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    OrderView findByOrderID(Long id);

    @Query("SELECT NEW org.example.dto.OrderCountDTO(o.user_order, o.restaurant, COUNT(o)) FROM Order o GROUP BY o.user_order, o.restaurant")
    List<OrderCountDTO> countOrdersByUserAndRestaurant();

}

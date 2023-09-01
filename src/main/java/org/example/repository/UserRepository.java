package org.example.repository;

import org.example.dto.userimp.UserView;
import org.example.model.UserImp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserImp, Long> {
    UserImp findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT DISTINCT u FROM UserImp u JOIN u.orders o GROUP BY u HAVING SUM(o.totalCost) > :thresholdAmount")
    List<UserView> findUsersWithTotalSumCostExceedingThreshold(@Param("thresholdAmount") int thresholdAmount);
}

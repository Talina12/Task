package com.food4good.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.food4good.database.entities.Orders;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findById(long orderId);
    List<Orders> findAll();
    Optional<Orders> findByUserId(long  userId);
    Optional<Orders> findByIdAndUserId(long orderId,long userId);
}

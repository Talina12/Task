package com.food4good.database.repositories;

import com.food4good.database.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findById(long orderId);
    List<Orders> findAll();
    Optional<Orders> findByUser(User user);
    Optional<Orders> findByIdAndUser(long orderId,User user);
    List <Orders> findAllByUser(User user);
    List <Orders> findByProductsIn(List<OrderProducts> products);

    
    
}

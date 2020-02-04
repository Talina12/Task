package com.food4good.database.repositories;

import com.food4good.database.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food4good.database.entities.OrderProducts;
import com.food4good.database.entities.Orders;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProducts, Long>{
	List<OrderProducts> findByOrders(Orders order);
}

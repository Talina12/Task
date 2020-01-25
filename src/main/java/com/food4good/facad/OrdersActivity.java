package com.food4good.facad;

import com.food4good.database.repositories.OrdersRepository;
import com.food4good.database.repositories.UsersRepository;
import com.food4good.database.entities.Orders;
import com.food4good.dto.OrderDTO;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersActivity {
    OrdersRepository ordersRepository;
    UsersRepository usersRepository;

    public OrdersActivity(OrdersRepository ordersRepository,  UsersRepository usersRepository) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
    }
    public List<OrderDTO> geOrdersByUser() {
        return null;
    }

    public void cancelOrder(long orderId,long userId)throws Exception {
        Orders order = ordersRepository.findByIdAndUserId(orderId, userId).orElseThrow(() -> new Exception("cannot find oredr for user id"));
        order.setStatus(OrderStatus.CANCELED.getStatus());
        ordersRepository.save(order);
    }
}

package com.food4good.factory;

import com.food4good.database.entities.User;
import com.food4good.database.repositories.OrdersRepository;
import com.food4good.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;

public class UserOrderList implements OrderListHandler {
    OrdersRepository ordersRepository;

    public UserOrderList(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public List<OrderDTO> getOrderList(User user) {
        List<OrderDTO> orderDTOList=new ArrayList<>();
        ordersRepository.findAllByUser(user).forEach(order ->orderDTOList.add(OrderDTO.convertFromEntity(order)));
        return orderDTOList;
    }
}

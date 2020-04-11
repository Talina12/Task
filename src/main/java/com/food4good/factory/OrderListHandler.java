package com.food4good.factory;

import com.food4good.database.entities.User;
import com.food4good.dto.OrderDTO;

import java.util.List;

public interface OrderListHandler {

    List<OrderDTO> getOrderList(User user);
}

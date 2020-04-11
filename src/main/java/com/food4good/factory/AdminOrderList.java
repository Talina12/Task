package com.food4good.factory;

import com.food4good.database.entities.OrderProducts;
import com.food4good.database.entities.Orders;
import com.food4good.database.entities.Products;
import com.food4good.database.entities.User;
import com.food4good.database.repositories.OrderProductsRepository;
import com.food4good.database.repositories.OrdersRepository;
import com.food4good.database.repositories.ProductsRepository;
import com.food4good.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderList implements OrderListHandler{
    OrdersRepository ordersRepository;
    OrderProductsRepository orderProductsRepository;
    ProductsRepository productsRepository;

    public AdminOrderList(OrdersRepository ordersRepository, OrderProductsRepository orderProductsRepository,ProductsRepository productsRepository) {
        this.ordersRepository = ordersRepository;
        this.orderProductsRepository = orderProductsRepository;
        this.productsRepository=productsRepository;
    }

    @Override
    public List<OrderDTO> getOrderList(User user) {
        List<OrderDTO> result=new ArrayList<>();
        List<Products> productsList=productsRepository.findBySupplier(user.getSupplier());
        List<OrderProducts> orderProductsList=orderProductsRepository.findByProductsIn(productsList);
        if(orderProductsList!=null)
        {
            ordersRepository.findByProductsIn(orderProductsList).forEach((order)->result.add(OrderDTO.convertFromEntity(order)));
        }
        return result;
    }
}

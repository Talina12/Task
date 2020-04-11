package com.food4good.factory;

import com.food4good.config.Roles;
import com.food4good.database.repositories.OrderProductsRepository;
import com.food4good.database.repositories.OrdersRepository;
import com.food4good.database.repositories.ProductsRepository;

public class AllOrdersFactory {
   OrdersRepository ordersRepository;
   OrderProductsRepository orderProductsRepository;
   ProductsRepository productsRepository;

   public AllOrdersFactory(OrdersRepository ordersRepository, OrderProductsRepository orderProductsRepository,ProductsRepository productsRepository) {
      this.ordersRepository = ordersRepository;
      this.orderProductsRepository = orderProductsRepository;
      this.productsRepository=productsRepository;
   }

   public OrderListHandler getOrderListHandler(String role){

      if(role == null){
         return null;
      }
      if(role.equalsIgnoreCase(Roles.ADMIN.toString())){
         return new AdminOrderList(ordersRepository,orderProductsRepository,productsRepository);

      } if(role.equalsIgnoreCase(Roles.SUPER_ADMIN.toString())){
         return new SuperAdminOrderList(ordersRepository);

      }  if(role.equalsIgnoreCase(Roles.USER.toString())){
         return new UserOrderList(ordersRepository);
      }
      return null;
   }
}
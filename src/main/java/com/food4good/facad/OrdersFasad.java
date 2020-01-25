package com.food4good.facad;

import java.util.HashSet;
import java.util.Iterator;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.food4good.database.entities.OrderProducts;
import com.food4good.database.entities.OrderStatus;
import com.food4good.database.entities.Orders;
import com.food4good.database.entities.Products;
import com.food4good.database.entities.User;
import com.food4good.database.repositories.OrderProductsRepository;
import com.food4good.database.repositories.OrdersRepository;
import com.food4good.database.repositories.ProductsRepository;
import com.food4good.database.repositories.UsersRepository;
import com.food4good.dto.NewOrderProductRequest;
import com.food4good.dto.NewOrderResponse;

@Service
public class OrdersFasad {
 OrdersRepository ordersReppository;
 UsersRepository usersRepository;
 ProductsRepository productsRepository;
 OrderProductsRepository orderProductsRepository;
 
 public OrdersFasad(OrdersRepository ordersReppository, UsersRepository usersRepository, 
		 ProductsRepository productsRepository, OrderProductsRepository orderProductsRepository) {
	 this.ordersReppository= ordersReppository;
	 this.usersRepository=usersRepository;
	 this.productsRepository= productsRepository;
	 this.orderProductsRepository=orderProductsRepository;
 }
 
 public NewOrderResponse addOrder(Long userId, NewOrderProductRequest[] productsRows, String comments) throws Exception  {
	Orders newOrder= new Orders();
	User user=usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
	newOrder.setUsers(user);
	newOrder.setComments(comments);
	newOrder.setStatus(OrderStatus.INIT.getTitle());
	newOrder=ordersReppository.save(newOrder);
	HashSet<OrderProducts> productSet  = new HashSet<OrderProducts>();
	for (NewOrderProductRequest row:productsRows) {
	 Products product=productsRepository.findById(row.getProductId()).orElseThrow(() -> new EntityNotFoundException("product not found"));
	 OrderProducts newOrderProduct= new OrderProducts();
	 newOrderProduct.setAmount(row.getProductAmount());
	 newOrderProduct.setOrders(newOrder);
	 if (product.getFixPrice()!=null) newOrderProduct.setPrice(product.getFixPrice());
	 else newOrderProduct.setPrice(product.getMaxPrice());
	 newOrderProduct.setProducts(product);
	 newOrderProduct= orderProductsRepository.save(newOrderProduct);
	 productSet.add(newOrderProduct);
	 }
	newOrder.setProducts(productSet);
	double totalPrice=0;
	for (OrderProducts orderProduct :productSet)
	 totalPrice=totalPrice+Double.parseDouble(orderProduct.getPrice())* orderProduct.getAmount();	
	newOrder.setTotalPrice(String.valueOf(totalPrice));
	newOrder =ordersReppository.save(newOrder);
	return new NewOrderResponse(newOrder);
 }

}


package com.food4good.facad;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.food4good.dto.OrderDTO;
import org.springframework.stereotype.Service;

import com.food4good.database.entities.OrderProducts;
import com.food4good.database.entities.Orders;
import com.food4good.database.entities.Products;
import com.food4good.database.entities.User;
import com.food4good.database.repositories.OrderProductsRepository;
import com.food4good.database.repositories.OrdersRepository;
import com.food4good.database.repositories.ProductsRepository;
import com.food4good.database.repositories.UsersRepository;
import com.food4good.dto.NewOrderProductRequest;
import com.food4good.dto.NewOrderRequest;
import com.food4good.dto.NewOrderResponse;


@Service
public class OrdersService {
 OrdersRepository ordersReppository;
 UsersRepository usersRepository;
 ProductsRepository productsRepository;
 OrderProductsRepository orderProductsRepository;
 
 public OrdersService(OrdersRepository ordersReppository, UsersRepository usersRepository,
		 ProductsRepository productsRepository, OrderProductsRepository orderProductsRepository) {
	 this.ordersReppository= ordersReppository;
	 this.usersRepository=usersRepository;
	 this.productsRepository= productsRepository;
	 this.orderProductsRepository=orderProductsRepository;
 }
 
 public NewOrderResponse addOrder(NewOrderRequest orderRequest, User user) throws Exception  {
	Orders newOrder= new Orders();
	newOrder.setUser(user);
	newOrder.setComments(orderRequest.getComments());
	newOrder.setStatus(OrderStatus.NEW.getStatus());
	newOrder=ordersReppository.save(newOrder);

	HashSet<OrderProducts> productSet  = new HashSet<OrderProducts>();
	 List<NewOrderProductRequest> productsRows = orderRequest.getProductsRows();
	 for (NewOrderProductRequest row: productsRows) {
      productSet.add(createOrderProduct(row,newOrder));
	 }
	
	newOrder.setProducts(productSet);
	
	newOrder.setTotalPrice();
	newOrder =ordersReppository.save(newOrder);
	return new NewOrderResponse(newOrder);
 }
	public List<OrderDTO> geOrdersByUser() {
		return null;
	}

	public void cancelOrder(long orderId,User user)throws Exception {
		Orders order = ordersReppository.findByIdAndUser(orderId, user).orElseThrow(() -> new Exception("cannot find this order for user id"));
		order.setStatus(OrderStatus.CANCELED.getStatus());
		ordersReppository.save(order);
	}
	
	protected OrderProducts createOrderProduct(NewOrderProductRequest row, Orders newOrder) {
		Products product=productsRepository.findById(row.getProductId()).orElseThrow(() -> new EntityNotFoundException("product not found"));
		OrderProducts newOrderProduct= new OrderProducts();
		 newOrderProduct.setAmount(row.getProductAmount());
		 newOrderProduct.setOrders(newOrder);
		 if (product.getFixPrice()!=null) newOrderProduct.setPrice(product.getFixPrice());
		 else newOrderProduct.setPrice(product.getMaxPrice());
		 newOrderProduct.setProducts(product);
		 return (orderProductsRepository.save(newOrderProduct));
	}

}


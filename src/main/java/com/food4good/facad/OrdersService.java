package com.food4good.facad;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.food4good.config.BadRequestException;
import com.food4good.config.GlobalProperties;
import com.food4good.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food4good.database.entities.OrderProducts;
import com.food4good.database.entities.Orders;
import com.food4good.database.entities.Products;
import com.food4good.database.entities.Supplier;
import com.food4good.database.entities.User;
import com.food4good.database.repositories.OrderProductsRepository;
import com.food4good.database.repositories.OrdersRepository;
import com.food4good.database.repositories.ProductsRepository;
import com.food4good.database.repositories.SupplierRepository;
import com.food4good.database.repositories.UsersRepository;


@Service
public class OrdersService {

 OrdersRepository ordersReppository;
 UsersRepository usersRepository;
 ProductsRepository productsRepository;
 OrderProductsRepository orderProductsRepository;
 private SupplierRepository supplierRepository;
 private final int hoursBeforeClose;

 @Autowired
 public OrdersService(OrdersRepository ordersReppository, UsersRepository usersRepository,
					  ProductsRepository productsRepository, OrderProductsRepository orderProductsRepository,
					  SupplierRepository supplierRepository,GlobalProperties globalProperties) {
	 this.ordersReppository= ordersReppository;
	 this.usersRepository=usersRepository;
	 this.productsRepository= productsRepository;
	 this.orderProductsRepository=orderProductsRepository;
	 this.supplierRepository=supplierRepository;
	 this.hoursBeforeClose = globalProperties.getHoursBeforeClose();
 }
 
 public NewOrderResponse addOrder(NewOrderRequest orderRequest, User user) throws Exception  {
	Orders newOrder= new Orders();
	Supplier supplier = supplierRepository.findById(orderRequest.getSupplierId()).orElseThrow(() -> new EntityNotFoundException("supplier not found"));
	newOrder.setUser(user);
	newOrder.setComments(orderRequest.getComments());
	newOrder.setStatus(OrderStatus.NEW.getStatus());
	newOrder=ordersReppository.save(newOrder);

	HashSet<OrderProducts> productSet  = new HashSet<OrderProducts>();
	 List<NewOrderProductRequest> productsRows = orderRequest.getProductsRows();
	 for (NewOrderProductRequest row: productsRows) {
      productSet.add(createOrderProduct(row,newOrder, supplier.getId() ));
	 }
	
	newOrder.setProducts(productSet);
	newOrder.setTotalPrice();
	newOrder =ordersReppository.save(newOrder);
	return new NewOrderResponse(newOrder);
 }
	public List<OrderDTO> getOrdersByUser(User user ) {
		List<Orders> orders= ordersReppository.findAllByUser(user);
		ArrayList<OrderDTO> ordersDto= new ArrayList<OrderDTO>();
		orders.forEach((o)->ordersDto.add(OrderDTO.convertFromEntity(o)));
		return ordersDto;
	}

	public void setOrderStatus(long orderId,User user,OrderStatus status)throws Exception {
		Orders order = ordersReppository.findByIdAndUser(orderId, user).orElseThrow(() -> new EntityNotFoundException("cannot find this order for user id"));
		if(status.equals(OrderStatus.CANCELED)) validateHoursRangeBeforeClose(order);
		order.setStatus(status.getStatus());
		ordersReppository.save(order);
	}
	
	public void validateHoursRangeBeforeClose(Orders order)throws Exception{
		Set<OrderProducts> orderProductsSet = order.getProducts();
		for (OrderProducts orderProduct:orderProductsSet)
		{
			Products product=productsRepository.findById(orderProduct.getProducts().getId()).orElseThrow(() -> new EntityNotFoundException("product not found"));
			String pickUpTime = product.getSupplier().getOpenHours();
			if(pickUpTime!=null&&!pickUpTime.equals(""))
			{
				if(!checkHoursRange(pickUpTime, hoursBeforeClose))
					throw new BadRequestException("time range not valid");
			}
		}
	}


	public boolean checkHoursRange(String openHours, int diff){
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		String token = "-";
		String openHour = openHours.substring(0, openHours.indexOf(token)).trim();
		String closeHour = openHours.substring(openHours.indexOf(token) + 1, openHours.length()).trim();
		Calendar cal1 = Calendar.getInstance();
		String[] parts = closeHour.split(":");
		cal1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
		cal1.set(Calendar.MINUTE, Integer.parseInt(parts[1]));

		LocalTime localTime = LocalTime.now();
		int hour = localTime.get(ChronoField.CLOCK_HOUR_OF_DAY);
		int minute = localTime.get(ChronoField.MINUTE_OF_HOUR);

		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR_OF_DAY, hour);
		cal2.set(Calendar.MINUTE, minute);

		long seconds = (cal1.getTimeInMillis() - cal2.getTimeInMillis()) / 1000;
		int hours = (int) (seconds / 3600);
		if (hours >= diff) return true;

		return false;
	}


	protected OrderProducts createOrderProduct(NewOrderProductRequest row, Orders newOrder, long supplierId) throws Exception {
		Products product=productsRepository.findById(row.getProductId()).orElseThrow(() -> new EntityNotFoundException("product not found"));
		if(product.getSupplier().getId()!=supplierId) throw new Exception("the product does not belong to the suppplier");
		OrderProducts newOrderProduct= new OrderProducts();
		 newOrderProduct.setAmount(row.getProductAmount());
		 newOrderProduct.setOrders(newOrder);
		 if (product.getFixPrice()!=null) newOrderProduct.setPrice(product.getFixPrice());
		 else newOrderProduct.setPrice(product.getMaxPrice());
		 newOrderProduct.setProducts(product);
		 return (orderProductsRepository.save(newOrderProduct));
	}

	public NewOrderResponse updateOrder(UpdateOrderRequest orderRequest, User user) throws Exception {
	    Orders order = ordersReppository.findByIdAndUser(orderRequest.getOrderId(), user).orElseThrow(() -> new EntityNotFoundException("cannot find this order for user id"));
	    Supplier supplier = supplierRepository.findById(orderRequest.getSupplierId()).orElseThrow(() -> new EntityNotFoundException("supplier not found"));
	    order.setComments(orderRequest.getComments());
		order.setStatus(OrderStatus.NEW.getStatus());
		deleteOrderProducts(order);
		for (NewOrderProductRequest row: orderRequest.getProductsRows())
			order.getProducts().add(createOrderProduct(row,order,supplier.getId()));
		order.setTotalPrice();
		return new NewOrderResponse(ordersReppository.save(order));
	}
	
	protected void deleteOrderProducts(Orders order) {
		order.getProducts().stream().forEach((p)->orderProductsRepository.delete(p));
		order.getProducts().clear();
	}

	public List<OrderDTO> getAll() {
		ArrayList<OrderDTO> ordersInfo = new ArrayList<OrderDTO>();
		ordersReppository.findAll().forEach((o)->ordersInfo.add(OrderDTO.convertFromEntity(o)));
		return ordersInfo;
	}
	
	public Long getUndelivered(User user) {
		return ordersReppository.findAllByUser(user).stream().filter((o)->o.getStatus().equals(OrderStatus.NEW.getStatus())).count();
		}

}


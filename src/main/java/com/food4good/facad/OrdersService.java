package com.food4good.facad;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.*;
import javax.persistence.EntityNotFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food4good.config.BadRequestException;
import com.food4good.config.GlobalProperties;
import com.food4good.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
		if(status.equals(OrderStatus.CANCELED)) 
		{
			validateHoursRangeBeforeClose(order);
			order.getProducts().forEach(p->updateProductAmount(p.getProducts(), p.getAmount()*-1));
			};
		order.setStatus(status.getStatus());
		ordersReppository.save(order); 
	}
	
	public void validateHoursRangeBeforeClose(Orders order)throws Exception{
		Set<OrderProducts> orderProductsSet = order.getProducts();
		for (OrderProducts orderProduct:orderProductsSet)
		{
			Products product=productsRepository.findById(orderProduct.getProducts().getId()).orElseThrow(() -> new EntityNotFoundException("product not found"));

				if(!checkHoursRange(product.getSupplier().getOpenHours(), hoursBeforeClose))
					throw new BadRequestException("time range not valid");

		}
	}


	public boolean checkHoursRange(String openHours, int diff) throws JsonProcessingException {
		ObjectMapper objectMapper=new ObjectMapper();
		Map<String,String> openHoursMap=objectMapper.readValue(openHours,Map.class);
		Calendar cal = Calendar.getInstance();
		String todayDay = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
		String openHoursToday=openHoursMap.get(todayDay);
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		String token = "-";
		String openHour = openHoursToday.substring(0, openHoursToday.indexOf(token)).trim();
		String closeHour = openHoursToday.substring(openHoursToday.indexOf(token) + 1, openHoursToday.length()).trim();
		Calendar cal1 = Calendar.getInstance();
		String[] parts = closeHour.split(":");
		cal1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));

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
		if(product.getSupplier().getId()!=supplierId) {
			deleteOrderProducts(newOrder);
			ordersReppository.delete(newOrder);
			throw new BadRequestException("the product does not belong to the suppplier");
			};
		OrderProducts newOrderProduct= new OrderProducts();
		newOrderProduct.setAmount(updateProductAmount(product,row.getProductAmount()));
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
		Set<OrderProducts> allProducts = order.getProducts();
		for (OrderProducts op:allProducts) {
			updateProductAmount(op.getProducts(), op.getAmount()*-1);
			orderProductsRepository.delete(op);
		}
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
	
	private Integer updateProductAmount(Products product, int productAmount) {
		int newAmount=product.getRealAmount()-productAmount;
		if (newAmount<0) 
		{
			newAmount=product.getAmount();
			product.setRealAmount(0);
		  }
		else 
		{
			product.setRealAmount(newAmount);
			newAmount=productAmount;
			}
		productsRepository.save(product);
		return newAmount;
	}

	@Scheduled(cron = "0 0 6 * * ?", zone = "UTC")
	public void setOriginProductAmount() {
		List<Supplier> allSuppliers = supplierRepository.findAll();
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		for(Supplier supplier:allSuppliers) {
			List<Products>supplierProducts = productsRepository.findBySupplier(supplier);
			java.util.stream.Stream<Integer> workingDays = getWorkingDays(supplier);
			if (workingDays.anyMatch(Integer.valueOf(calendar.get(Calendar.DAY_OF_WEEK))::equals)) 
			  supplierProducts.forEach(s -> resetProductAmount(s));	
		}
	}

	private java.util.stream.Stream<Integer> getWorkingDays(Supplier supplier) {
		String openHours = supplier.getOpenHours();
		String [] days;
		if (openHours!=null&&!openHours.equals("")) {
			days = openHours.split(","); 
			days[0] = days[0].substring(1);
	 	}
		else days = new String[] {"1","2","3","4","5","6","7"}; 
		return Arrays.stream(days).map(s -> Integer.valueOf(s.substring(1, 2)));
	}
	
	private void resetProductAmount(Products product) {
		int amount = product.getAmount();
		product.setRealAmount(amount);
		productsRepository.save(product);
	}
}


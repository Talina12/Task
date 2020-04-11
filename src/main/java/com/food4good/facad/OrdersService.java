package com.food4good.facad;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import javax.persistence.EntityNotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food4good.config.BadRequestException;
import com.food4good.config.GlobalProperties;
import com.food4good.dto.*;
import com.food4good.factory.AllOrdersFactory;
import com.food4good.factory.OrderListHandler;
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
		if(status.equals(OrderStatus.CANCELED)) {
			Set<OrderProducts> orderProducts = order.getProducts();
			validateHoursRangeBeforeClose(orderProducts);
			orderProducts.forEach(p->updateProductAmount(p.getProducts(), p.getAmount()*-1));
			};
		order.setStatus(status.getStatus());
		ordersReppository.save(order); 
	}
	
	public void validateHoursRangeBeforeClose(Set<OrderProducts> orderProducts) throws Exception{
		OrderProducts anyOrderProduct = orderProducts.stream().findAny().orElseThrow(() -> new EntityNotFoundException("product not found"));
		Supplier supplier = anyOrderProduct.getProducts().getSupplier();
		if(!checkHoursRange(supplier.getOpenHours(), hoursBeforeClose))
			throw new BadRequestException("time range not valid");
	}
		
    public boolean checkHoursRange(String openHours, int diff) throws Exception  {
		ObjectMapper objectMapper=new ObjectMapper();
		Map <String,String> openHoursMap = objectMapper.readValue(openHours,Map.class);
		Calendar cal = Calendar.getInstance();
		String todayDay = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
		String openHoursToday=openHoursMap.get(todayDay);
		LocalTime now = ZonedDateTime.now(ZoneId.of("Israel")).toLocalTime();
		if (now.isAfter(getCloseHour(openHoursToday).minusHours(diff)))
				return false; 
			else return true;
		}
		
	private LocalTime getCloseHour(String workingHours) throws ParseException {
		int dif = workingHours.indexOf('-');
		workingHours = workingHours.substring(dif+1).trim();
		LocalTime closingTime = LocalTime.parse(workingHours);
		return closingTime;
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

	public List<OrderDTO> getAll(User user) {
		List<OrderDTO> ordersInfoList = new ArrayList<OrderDTO>();
		AllOrdersFactory allOrdersFactory=new AllOrdersFactory(ordersReppository,orderProductsRepository,productsRepository);
		OrderListHandler orderListHandler=allOrdersFactory.getOrderListHandler(user.getRoles());
		if(orderListHandler!=null) {
			ordersInfoList = orderListHandler.getOrderList(user);
		}
		return ordersInfoList;
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
}


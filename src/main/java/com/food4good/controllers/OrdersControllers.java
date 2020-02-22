
package com.food4good.controllers;

import com.food4good.database.entities.User;
import com.food4good.dto.OrderDTO;
import com.food4good.dto.UpdateOrderRequest;
import com.food4good.dto.NewOrderRequest;
import com.food4good.dto.NewOrderResponse;
import com.food4good.facad.OrderReport;
import com.food4good.facad.OrderStatus;
import com.food4good.facad.OrdersService;

import com.food4good.facad.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/order")
public class OrdersControllers {
    private final OrderReport orderReport;
    private UsersService usersService;
    private OrdersService ordersService;
    public OrdersControllers(UsersService usersService, OrderReport orderReport, OrdersService ordersService) {
        this.orderReport = orderReport;
        this.usersService=usersService;
        this.ordersService=ordersService;
    }

    @GetMapping(value = "/{supplierId}", produces = APPLICATION_JSON_VALUE)
    public List<OrderDTO> userById(@PathVariable("supplierId") Long supplierId) throws Exception {
        return orderReport.getById(supplierId);
    }
    
    @GetMapping(value = "/user", produces = APPLICATION_JSON_VALUE)
    public List<OrderDTO> getOrdersOfUser() throws Exception {
    	String userToken="456";
    	User user = usersService.getByToken(userToken);
    	return ordersService.getOrdersByUser(user);
    }
    
    @PostMapping (value="/init")
    public ResponseEntity<NewOrderResponse> createNewOrder(@Validated @RequestBody NewOrderRequest orderRequest) throws Exception {
    	String userToken="123";
    	User user = usersService.getByToken(userToken);
    	return ResponseEntity.ok(ordersService.addOrder(orderRequest, user));
    	
    }
    
    @PostMapping(path = "/cancel/{order_id}")
    public ResponseEntity cancelOrder(@PathVariable("order_id") long orderId) throws Exception {
        String userToken=" ";
        User user = usersService.getByToken(userToken);
        ordersService.setOrderStatus(orderId,user, OrderStatus.CANCELED);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/closed/{order_id}")
    public ResponseEntity closeOrder(@PathVariable("order_id") long orderId) throws Exception {
        String userToken=" ";
        User user = usersService.getByToken(userToken);
        ordersService.setOrderStatus(orderId,user, OrderStatus.DELIVERED);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping (value="/update")
    public ResponseEntity<NewOrderResponse> updateOrder(@Validated @RequestBody @NonNull UpdateOrderRequest orderRequest) throws Exception {
    	String userToken="123";
    	User user = usersService.getByToken(userToken);
    	return ResponseEntity.ok(ordersService.updateOrder(orderRequest, user));
    	
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
    	return ResponseEntity.ok(ordersService.getAll());
    	}

    @GetMapping("/undelivered")
    public ResponseEntity<Long> getUndeliveredOrders() throws Exception{
    	String userToken="123";
    	User user = usersService.getByToken(userToken);
    	return ResponseEntity.ok(ordersService.getUndelivered(user));
    }
}


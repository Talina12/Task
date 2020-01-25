package com.food4good.controllers;

import com.food4good.database.entities.User;
import com.food4good.dto.OrderDTO;
import com.food4good.dto.OrderReportDTO;
import com.food4good.facad.OrderReport;
import com.food4good.facad.OrdersActivity;
import com.food4good.facad.UsersFacad;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/order")
public class OrdersControllers {
    private UsersFacad usersFacad;
    private final OrderReport orderReport;
    private final OrdersActivity ordersActivity;
    public OrdersControllers(UsersFacad users, OrderReport orderReport, OrdersActivity ordersActivity) {
        this.orderReport = orderReport;
        this.ordersActivity = ordersActivity;
        this.usersFacad=usersFacad;
    }

    @GetMapping(value = "/{supplierId}", produces = APPLICATION_JSON_VALUE)
    public List<OrderReportDTO> userById(@PathVariable("supplierId") Long supplierId) throws Exception {
        return orderReport.getById(supplierId);
    }
    @GetMapping(value = "/user}", produces = APPLICATION_JSON_VALUE)
    public List<OrderDTO> getOrdersOfUser() throws Exception {
        return ordersActivity.geOrdersByUser();
    }

    @PostMapping(path = "/cancel/{order_id}")
    public ResponseEntity cancelOrder(@PathVariable("order_id") long orderId) throws Exception {
        String userToken=" ";
        User user = usersFacad.getByToken(userToken);
        ordersActivity.cancelOrder(orderId,user.getId());
        return ResponseEntity.ok().build();
    }



}

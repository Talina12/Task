package com.food4good.controllers;

import com.food4good.database.entities.User;
import com.food4good.dto.OrderDTO;
import com.food4good.dto.OrderReportDTO;
import com.food4good.facad.OrderReport;
import com.food4good.facad.OrdersActivity;
import com.food4good.facad.UsersFacad;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/order")
public class OrdersControllers {
    private final OrderReport orderReport;
    private final OrdersActivity ordersActivity;
    public OrdersControllers(UsersFacad users, OrderReport orderReport, OrdersActivity ordersActivity) {
        this.orderReport = orderReport;
        this.ordersActivity = ordersActivity;
    }

    @GetMapping(value = "/{suplierId}", produces = APPLICATION_JSON_VALUE)
    public List<OrderReportDTO> userById(@PathVariable("suplierId") Long suplierId) throws Exception {
        return orderReport.getById(suplierId);
    }
    @GetMapping(value = "/user}", produces = APPLICATION_JSON_VALUE)
    public List<OrderDTO> getOrdersOfUser() throws Exception {
        return ordersActivity.geOrdersByUser();
    }

}

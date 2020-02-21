package com.food4good.facad;

import com.food4good.database.entities.OrderProducts;
import com.food4good.database.entities.Orders;
import com.food4good.database.entities.Supplier;
import com.food4good.database.repositories.OrdersRepository;
import com.food4good.dto.OrderReportDTO;
import com.google.common.base.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderReport {
    private OrdersRepository ordersReppository;
        
    public OrderReport(OrdersRepository ordersReppository) {
    	this.ordersReppository=ordersReppository;
    	}
	
    public List<OrderReportDTO> getById(Long supplierId) {
        ArrayList <OrderReportDTO> ordersDto = new ArrayList <OrderReportDTO>();
    	List<Orders> selectedOrders= ordersReppository.findAll().stream()
    	                 .filter((o)->getSuplier(o)!=null && getSuplier(o).getId()==supplierId).collect(Collectors.toList());
        selectedOrders.forEach((o)->ordersDto.add(OrderReportDTO.convertFromEntity(o)));
    	return ordersDto;
    }
    
    public Supplier getSuplier(Orders order) {
		java.util.Optional<OrderProducts> orderProduct = order.getProducts().stream().findFirst();
    	if (orderProduct.isPresent())
			return orderProduct.get().getProducts().getSupplier();
		else {log.debug("order without products found");
		      return null;
		}
		}
}

package com.food4good.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food4good.dto.SupplierInfoDTO;
import com.food4good.facad.SupplierService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private SupplierService supplierService;
	
	public AdminController(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	
	@GetMapping("/all_suppliers")
	public  List<SupplierInfoDTO> getAllsuppliersInfo() throws Exception {
        return supplierService.getAllInfo();
    }
	
	@GetMapping("/active_suppliers")
	public  List<SupplierInfoDTO> getActivesuppliersInfo() throws Exception {
        return supplierService.getActiveInfo();
    }
}

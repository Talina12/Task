package com.food4good.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food4good.dto.ProductDTO;
import com.food4good.dto.SupplierInfoDTO;
import com.food4good.dto.SupplierPermanentDTO;
import com.food4good.facad.SupplierService;
import com.food4good.facad.UsersService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private SupplierService supplierService;
	private UsersService usersService;
	
	public AdminController(SupplierService supplierService, UsersService usersService) {
		this.supplierService = supplierService;
		this.usersService = usersService;
	}
	
	@PostMapping("/update")
	public ResponseEntity<Object>	updateSupplier(@Validated @NonNull @RequestBody SupplierPermanentDTO supplierPermanentDTO) throws Exception{
	    supplierService.updateSupplier(usersService.getByToken().getSupplier().getId(), supplierPermanentDTO);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	
	@PostMapping("/update/product")
	public ResponseEntity<Object> updateProducts(@Validated @NonNull @RequestBody ProductDTO productDTO) throws Exception{
		supplierService.updateProduct(usersService.getByToken().getSupplier(), productDTO);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("/supplier")
	public ResponseEntity<SupplierInfoDTO> getSupplierInfo() throws Exception{
		return ResponseEntity.ok(supplierService.getSupplierInfo(usersService.getByToken().getSupplier().getId()));
	}
	
	@GetMapping("/suppliers/active")
	public ResponseEntity<List<SupplierInfoDTO>> getActiveSuppliers() throws Exception{
		return ResponseEntity.ok(supplierService.getActiveInfo());
	}
	}

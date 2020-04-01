package com.food4good.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food4good.dto.SupplierInfoDTO;
import com.food4good.dto.SupplierPermanentDTO;
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
	
	@PostMapping("/supplier/{supplierId}")
	public ResponseEntity<Object>	updateSupplier(@PathVariable("supplierId") Long supplierId,
			      @Validated @NonNull @RequestBody SupplierPermanentDTO suplierPermanentDTO) throws Exception{
	    supplierService.updateSupplier(supplierId, suplierPermanentDTO);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

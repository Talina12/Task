package com.food4good.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food4good.database.repositories.SupplierRepository;
import com.food4good.dto.ProductDTO;
import com.food4good.dto.SupplierInfoDTO;
import com.food4good.dto.SupplierPermanentDTO;
import com.food4good.dto.UsersDTO;
import com.food4good.facad.SupplierService;
import com.food4good.facad.UsersService;


@RestController
@CrossOrigin
@RequestMapping("/superAdmin")
public class SuperAdminController {
	UsersService usersService;
	SupplierService supplierService;
	SupplierRepository supplierRepository;
	
    public SuperAdminController(UsersService usersService, SupplierService supplierService, 
    		                    SupplierRepository supplierRepository) {
		this.usersService = usersService;
		this.supplierService = supplierService;
		this.supplierRepository = supplierRepository;
	}


	
	@GetMapping("/allSuppliers")
	public ResponseEntity<List<SupplierInfoDTO>> getAllSuppliers() throws Exception{
		return ResponseEntity.ok(supplierService.getAllInfo());
	}
	
	@GetMapping("/activeSuppliers")
	public ResponseEntity<List<SupplierInfoDTO>> getActiveSuppliers() throws Exception{
		return ResponseEntity.ok(supplierService.getActiveInfo());
	}

	@PostMapping("/update/{supplierId}")
	public ResponseEntity<Object>	updateSupplier(@PathVariable("supplierId") @Valid @NotNull Long supplierId, @Validated @NonNull @RequestBody SupplierPermanentDTO supplierPermanentDTO) throws Exception{
	    supplierService.updateSupplier(supplierId, supplierPermanentDTO);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	
	@PostMapping("/update/product/{productId}")
	public ResponseEntity<Object> updateProducts(@PathVariable("productId") @Valid @NonNull Long productId, @Validated @NonNull @RequestBody ProductDTO productDTO) throws Exception{
		supplierService.updateProduct(productDTO);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	@CrossOrigin
	@PostMapping("/supplier/create")
	public ResponseEntity<SupplierInfoDTO> createSuppleir(@Validated @RequestBody SupplierInfoDTO supplierInfoDTO) throws Exception {
		return ResponseEntity.ok(supplierService.createSupplier(supplierInfoDTO));
	}
/*<<<<<<< HEAD

=======
	
	@GetMapping("/users")
	public ResponseEntity<List<UsersDTO>> getAllUsers(){
		return ResponseEntity.ok(usersService.getAll());
	}
	
	G
>>>>>>> stash*/
}

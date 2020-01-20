/*<<<<<<< HEAD
package com.food4good.controllers;


import com.food4good.database.entities.User;
import com.food4good.dto.SupplierDTO;
import com.food4good.dto.SupplierInfoDTO;
import com.food4good.facad.SupplierFacad;
import com.food4good.facad.UsersFacad;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/suppliers")
@Validated
public class SuppliersController {
    private SupplierFacad supplierFacad;
    private UsersFacad usersFacad;

    public SuppliersController(SupplierFacad supplierFacad, UsersFacad usersFacad) {
        this.supplierFacad=supplierFacad;
        this.usersFacad=usersFacad;
    }
    @GetMapping(value = "/{supplierId}", produces = APPLICATION_JSON_VALUE)
    public SupplierDTO supplierById(@PathVariable("supplierId") @Valid @NotNull Long supplierId) throws Exception {
        return supplierFacad.getById(supplierId);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<SupplierInfoDTO> getAllsuppliersInfo() throws Exception {
        return supplierFacad.getAllInfo();
    }
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public void setSupplier(@RequestBody String name) throws Exception {
        System.out.println("----------------------"+name);
    }
    
    @DeleteMapping(value ="/favorite/{rest_id}")//? int? produces = APPLICATION_JSON_VALUE ?
    public ResponseEntity<Long> deleteRate(@PathVariable("rest_id")@NotNull Long supplierId ) throws Exception {
    	String userToken=" ";
    	User user = usersFacad.getByToken(userToken);
    	return new ResponseEntity<> (supplierFacad.deleteRate(user.getId(),supplierId),HttpStatus.OK);
    	
    	}
    
}

=======*/
package com.food4good.controllers;


import com.food4good.database.entities.User;
import com.food4good.dto.SupplierDTO;
import com.food4good.dto.SupplierInfoDTO;
import com.food4good.facad.SupplierFacad;
import com.food4good.facad.UsersFacad;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/suppliers")
@Validated
public class SuppliersController {
    private SupplierFacad supplierFacad;
    private UsersFacad usersFacad;

    public SuppliersController(SupplierFacad supplierFacad, UsersFacad usersFacad) {
        this.supplierFacad=supplierFacad;
        this.usersFacad=usersFacad;
    }
    
    @GetMapping(value = "/{supplierId}", produces = APPLICATION_JSON_VALUE)
    public SupplierDTO supplierById(@PathVariable("supplierId") @Valid @NotNull Long supplierId) throws Exception {
        return supplierFacad.getById(supplierId);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<SupplierInfoDTO> getAllsuppliersInfo() throws Exception {
        return supplierFacad.getAllInfo();
    }


    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public void setSupplier(@RequestBody String name) throws Exception {
        System.out.println("----------------------"+name);
    }

    @PostMapping(path = "/favorite/{supplierId}")
    public void add2SupplierRate(@PathVariable("supplierId") long supplierId) throws Exception {
        supplierFacad.addSupplierRate(supplierId,Long.valueOf(1));
    }
    
    @DeleteMapping(value ="/favorite/{rest_id}")//? int? produces = APPLICATION_JSON_VALUE ?
    public ResponseEntity<Long> deleteRate(@PathVariable("rest_id")@NotNull Long supplierId ) throws Exception {
    	String userToken=" ";
    	User user = usersFacad.getByToken(userToken);
    	return new ResponseEntity<> (supplierFacad.deleteRate(user.getId(),supplierId),HttpStatus.OK);
    	
    	}
}
//>>>>>>> branch 'develop' of https://github.com/food4goodIL/backend_java.git


package com.food4good.controllers;


import com.food4good.database.entities.User;
import com.food4good.dto.SupplierDTO;
import com.food4good.dto.SupplierInfoDTO;
import com.food4good.facad.SupplierService;
import com.food4good.facad.UsersService;

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
    private SupplierService supplierService;
    private UsersService usersService;

    public SuppliersController(SupplierService supplierService, UsersService usersService) {
        this.supplierService=supplierService;
        this.usersService=usersService;
    }
    
    @GetMapping(value = "/{supplierId}", produces = APPLICATION_JSON_VALUE)
    public SupplierDTO supplierById(@PathVariable("supplierId") @Valid @NotNull Long supplierId) throws Exception {
        return supplierService.getById(supplierId);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<SupplierInfoDTO> getAllsuppliersInfo() throws Exception {
        return supplierService.getAllInfo();
    }

    @PostMapping(path = "/favorite/{supplierId}")
    public ResponseEntity addRate(@PathVariable("supplierId") long supplierId) throws Exception {
        String userToken=" ";
        User user = usersService.getByToken(userToken);
        int finalRate= supplierService.addSupplierRate(supplierId,user.getId());
        return ResponseEntity.ok(finalRate);
    }
    
    @DeleteMapping(value ="/favorite/{supplierId}")
    public ResponseEntity deleteRate(@PathVariable("supplierId") long supplierId ) throws Exception {
    	String userToken=" ";
    	User user = usersService.getByToken(userToken);
    	int finalRate=supplierService.deleteSupplierRate(supplierId,user.getId());
        return ResponseEntity.ok(finalRate);
    }
}

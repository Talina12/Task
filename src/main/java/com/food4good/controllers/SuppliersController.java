package com.food4good.controllers;


import com.food4good.dto.SupplierDTO;
import com.food4good.dto.SupplierInfoDTO;
import com.food4good.facad.SupplierFacad;

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

    public SuppliersController(SupplierFacad supplierFacad) {
        this.supplierFacad=supplierFacad;
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
}

package com.food4good.controllers;

import com.food4good.dto.OrderDTO;
import com.food4good.dto.OrderReportDTO;
import com.food4good.dto.SuplierDTO;
import com.food4good.dto.SuplierInfoDTO;
import com.food4good.facad.SuplierFacad;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/supliers")
@Validated
public class SupliersController {
    private SuplierFacad suplierFacad;

    public SupliersController(SuplierFacad suplierFacad) {
        this.suplierFacad=suplierFacad;
    }
    @GetMapping(value = "/{suplierId}", produces = APPLICATION_JSON_VALUE)
    public SuplierDTO suplierById(@PathVariable("suplierId") @Valid @NotNull Long suplierId) throws Exception {
        return suplierFacad.getById(suplierId);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<SuplierInfoDTO> getAllSupliersInfo() throws Exception {
        return suplierFacad.getAllInfo();
    }
}

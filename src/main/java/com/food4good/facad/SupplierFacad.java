package com.food4good.facad;

import com.food4good.database.entities.Supplier;
import com.food4good.database.repositories.SupplierRepository;
import com.food4good.dto.SupplierDTO;
import com.food4good.dto.SupplierInfoDTO;
import com.food4good.dto.SupplierInfoDTO;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierFacad {
    SupplierRepository supplierRepository;

    public SupplierFacad(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public SupplierDTO getById(Long supplierId) throws Exception {
        Supplier supplierEntity=supplierRepository.findById(supplierId).orElseThrow(()->new Exception("supplier not found"));
        SupplierDTO supplierDTO=SupplierDTO.convertFromEntity(supplierEntity);
        return  supplierDTO;
    }

    public List<SupplierInfoDTO> getAllInfo() {
        List<Supplier> all = supplierRepository.findAll();
        List<SupplierInfoDTO> supplierInfoDTOS = all.stream().map(SupplierInfoDTO::convertFromEntity).collect(Collectors.toList());
        return supplierInfoDTOS;
    }
}

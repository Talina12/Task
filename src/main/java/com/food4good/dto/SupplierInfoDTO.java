package com.food4good.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.food4good.database.entities.Supplier;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SupplierInfoDTO {
    @JsonUnwrapped
    SupplierDTO supplierDTO;
    private List<ProductDTO> productDTOList;

    public static SupplierInfoDTO convertFromEntity(Supplier supplier) {
        SupplierDTO supplierDTO = SupplierDTO.convertFromEntity(supplier);
        SupplierInfoDTO supplierInfoDTO=new SupplierInfoDTO();
        supplierInfoDTO.setSupplierDTO(supplierDTO);
        supplierInfoDTO.getSupplierDTO().setId(supplier.getId());
        supplierInfoDTO.getSupplierDTO().setCreatedAt(supplier.getCreatedAt().toString());
        supplierInfoDTO.getSupplierDTO().setUpdatedAt(supplier.getUpdatedAt().toString());
        List<ProductDTO> productDTOList = supplier.getProducts().stream().map(ProductDTO::convertFromEntity).collect(Collectors.toList());
        supplierInfoDTO.setProductDTOList(productDTOList);
        return supplierInfoDTO;
    }
}

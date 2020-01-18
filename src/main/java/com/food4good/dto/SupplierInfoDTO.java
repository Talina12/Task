package com.food4good.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.food4good.database.entities.Supplier;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SupplierInfoDTO  extends BaseDTO {
    @JsonUnwrapped
    SupplierDTO supplierDTO;
    private List<ProductDTO> productDTOList;

    public static SupplierInfoDTO convertFromEntity(Supplier supplier) {
        SupplierDTO supplierDTO = SupplierDTO.convertFromEntity(supplier);
        SupplierInfoDTO supplierInfoDTO=new SupplierInfoDTO();
        supplierInfoDTO.setSupplierDTO(supplierDTO);
        supplierInfoDTO.setId(supplier.getId());
        supplierInfoDTO.setCreatedAt(supplier.getCreatedAt().toString());
        supplierInfoDTO.setUpdatedAt(supplier.getUpdatedAt().toString());
        List<ProductDTO> productDTOList = supplier.getProducts().stream().map(ProductDTO::convertFromEntity).collect(Collectors.toList());
        supplierInfoDTO.setProductDTOList(productDTOList);
        return supplierInfoDTO;
    }
}

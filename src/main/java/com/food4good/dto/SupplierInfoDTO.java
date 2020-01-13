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
    private List<DishDTO> dishDTOList;

    public static SupplierInfoDTO convertFromEntity(Supplier supplier) {
        SupplierDTO supplierDTO = SupplierDTO.convertFromEntity(supplier);
        SupplierInfoDTO supplierInfoDTO=new SupplierInfoDTO();
        supplierInfoDTO.setSupplierDTO(supplierDTO);
        supplierInfoDTO.setId(supplier.getId());
        supplierInfoDTO.setCreatedAt(supplier.getCreatedAt().toString());
        supplierInfoDTO.setUpdatedAt(supplier.getUpdatedAt().toString());
        List<DishDTO> dishDTOList = supplier.getDishes().stream().map(DishDTO::convertFromEntity).collect(Collectors.toList());
        supplierInfoDTO.setDishDTOList(dishDTOList);
        return supplierInfoDTO;
    }
}

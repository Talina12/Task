package com.food4good.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food4good.database.entities.Supplier;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class SupplierInfoDTO {
    @JsonUnwrapped
    SupplierDTO supplierDTO;
    private List<ProductDTO> productDTOList;

    public static SupplierInfoDTO convertFromEntity(Supplier supplier) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        SupplierDTO supplierDTO = SupplierDTO.convertFromEntity(supplier);
        SupplierInfoDTO supplierInfoDTO=new SupplierInfoDTO();
        supplierInfoDTO.setSupplierDTO(supplierDTO);
        supplierInfoDTO.getSupplierDTO().setId(supplier.getId());
        supplierInfoDTO.getSupplierDTO().setCreatedAt(supplier.getCreatedAt().toString());
        supplierInfoDTO.getSupplierDTO().setUpdatedAt(supplier.getUpdatedAt().toString());
        supplierInfoDTO.getSupplierDTO().setOpenHours(objectMapper.readValue(supplier.getOpenHours(), Map.class));
        supplierInfoDTO.getSupplierDTO().setIsActive(String.valueOf(supplier.isActive()));
        List<ProductDTO> productDTOList = supplier.getProducts().stream().map(ProductDTO::convertFromEntity).collect(Collectors.toList());
        supplierInfoDTO.setProductDTOList(productDTOList);
        return supplierInfoDTO;
    }
    public static Supplier convertToEntity(SupplierInfoDTO supplierInfoDTO) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        Supplier supplier = new Supplier();
        supplier.setAddress(supplierInfoDTO.getSupplierDTO().getAddress());
        supplier.setBackGroundImage(supplierInfoDTO.getSupplierDTO().getBackgroundImage());
        supplier.setLogoImage(supplierInfoDTO.getSupplierDTO().getLogoImage());
        supplier.setLatetude(supplierInfoDTO.getSupplierDTO().getLatetude());
        supplier.setLongtitude(supplierInfoDTO.getSupplierDTO().getLongtitude());
        supplier.setName(supplierInfoDTO.getSupplierDTO().getSupplierName());
        supplier.setActive(Boolean.valueOf(supplierInfoDTO.getSupplierDTO().getIsActive()));
        String openHours = objectMapper.writeValueAsString(supplierInfoDTO.getSupplierDTO().getOpenHours());
        supplier.setOpenHours(openHours);
        return supplier;
    }
}

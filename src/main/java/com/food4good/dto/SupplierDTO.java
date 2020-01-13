package com.food4good.dto;

import com.food4good.database.entities.Supplier;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class SupplierDTO extends BaseDTO{
    private String supplierName;
    private String address;
    private String openHours;
    private String image;
    private String longtitude;
    private String latetude;



    public static SupplierDTO convertFromEntity(Supplier supplier) {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setAddress(supplier.getAddress());
        supplierDTO.setImage(supplier.getImage());
        supplierDTO.setLatetude(supplier.getLatetude());
        supplierDTO.setLongtitude(supplier.getLongtitude());
        supplierDTO.setOpenHours(supplier.getOpenHours());
        supplierDTO.setSupplierName(supplier.getName());
        return supplierDTO;
    }
}

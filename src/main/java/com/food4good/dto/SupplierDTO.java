package com.food4good.dto;

import com.food4good.database.entities.Supplier;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter

public class SupplierDTO extends BaseDTO{
    private String supplierName;
    private String address;
    private Map<String,String> openHours;
    private String backgroundImage;
    private String logoImage;
    private String longtitude;
    private String latetude;
    private String rates;
    private String displayOrder;
    private String isActive;
    
    public static SupplierDTO convertFromEntity(Supplier supplier) {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setId(supplier.getId());
        supplierDTO.setAddress(supplier.getAddress());
        supplierDTO.setBackgroundImage(supplier.getBackGroundImage());
        supplierDTO.setLogoImage(supplier.getLogoImage());
        supplierDTO.setLatetude(supplier.getLatetude());
        supplierDTO.setLongtitude(supplier.getLongtitude());
       // supplierDTO.setOpenHours(supplier.getOpenHours());
        supplierDTO.setSupplierName(supplier.getName());
        supplierDTO.setRates(supplier.getRates());
        supplierDTO.setDisplayOrder(supplier.getDisplayOrder());
        supplierDTO.setIsActive(String.valueOf(supplier.isActive()));
        return supplierDTO;
    }
}

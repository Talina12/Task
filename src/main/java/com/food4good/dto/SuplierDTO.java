package com.food4good.dto;

import com.food4good.database.entities.Suplier;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter

public class SuplierDTO extends BaseDTO{
    private String suplierName;
    private String address;
    private String openHours;
    private String image;
    private String longtitude;
    private String latetude;



    public static SuplierDTO convertFromEntity(Suplier suplier) {
        SuplierDTO suplierDTO = new SuplierDTO();
        suplierDTO.setAddress(suplier.getAddress());
        suplierDTO.setImage(suplier.getImage());
        suplierDTO.setLatetude(suplier.getLatetude());
        suplierDTO.setLongtitude(suplier.getLongtitude());
        suplierDTO.setOpenHours(suplier.getOpenHours());
        suplierDTO.setSuplierName(suplier.getName());
        return suplierDTO;
    }
}

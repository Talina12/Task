package com.food4good.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.food4good.database.entities.Suplier;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SuplierInfoDTO  extends BaseDTO {
    @JsonUnwrapped
    SuplierDTO suplierDTO;
    private List<DishDTO> dishDTOList;

    public static SuplierInfoDTO convertFromEntity(Suplier suplier) {
        SuplierDTO suplierDTO = SuplierDTO.convertFromEntity(suplier);
        SuplierInfoDTO suplierInfoDTO=new SuplierInfoDTO();
        suplierInfoDTO.setSuplierDTO(suplierDTO);
        suplierInfoDTO.setId(suplier.getId());
        suplierInfoDTO.setCreatedAt(suplier.getCreatedAt().toString());
        suplierInfoDTO.setUpdatedAt(suplier.getUpdatedAt().toString());
        List<DishDTO> dishDTOList = suplier.getDishes().stream().map(DishDTO::convertFromEntity).collect(Collectors.toList());
        suplierInfoDTO.setDishDTOList(dishDTOList);
        return suplierInfoDTO;
    }
}

package com.food4good.facad;

import com.food4good.database.entities.Suplier;
import com.food4good.database.repositories.SuplierRepository;
import com.food4good.dto.SuplierDTO;
import com.food4good.dto.SuplierInfoDTO;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuplierFacad {
    SuplierRepository suplierRepository;

    public SuplierFacad(SuplierRepository suplierRepository) {
        this.suplierRepository = suplierRepository;
    }

    public SuplierDTO getById(Long suplierId) throws Exception {
        Suplier suplierEntity=suplierRepository.findById(suplierId).orElseThrow(()->new Exception("suplier not found"));
        SuplierDTO suplierDTO=SuplierDTO.convertFromEntity(suplierEntity);
        return  suplierDTO;
    }

    public List<SuplierInfoDTO> getAllInfo() {
        List<Suplier> all = suplierRepository.findAll();
        List<SuplierInfoDTO> suplierInfoDTOS = all.stream().map(SuplierInfoDTO::convertFromEntity).collect(Collectors.toList());
        return suplierInfoDTOS;
    }
}

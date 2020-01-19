package com.food4good.facad;

import com.food4good.database.entities.Supplier;
import com.food4good.database.entities.SupplierRate;
import com.food4good.database.entities.User;
import com.food4good.database.repositories.SupplierRepository;
import com.food4good.database.repositories.SupplierRateRepository;
import com.food4good.database.repositories.UsersRepository;
import com.food4good.dto.SupplierDTO;
import com.food4good.dto.SupplierInfoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierFacad {
    SupplierRepository supplierRepository;
    SupplierRateRepository supplierRateRepository;
    UsersRepository usersRepository;

    public SupplierFacad(SupplierRepository supplierRepository, SupplierRateRepository suppliersRateRepository, UsersRepository usersRepository) {
        this.supplierRepository = supplierRepository;
        this.usersRepository = usersRepository;
        this.supplierRateRepository = suppliersRateRepository;
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

    public int getAllSupplierRate(Long supplierId){
        Optional<Supplier> supplierOptional=supplierRepository.findById(supplierId);
        if(supplierOptional.isPresent()){
            Supplier supplier = supplierOptional.get();
            List<SupplierRate> list = supplierRateRepository.findBySupplier(supplier);
            return list.size();
        }
        return 0;
    }




    public void addSupplierRate(Long supplierId,Long userId) throws Exception
    {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(()->new Exception("supplier not found"));
        User user = usersRepository.findById(userId).orElseThrow(()->new Exception("user not found"));

            Optional<SupplierRate> supplierRate =  supplierRateRepository.findBySupplierAndUser(supplier,user);
            if(!supplierRate.isPresent())
            {
                SupplierRate supplierRateEntity = new SupplierRate();
                supplierRateEntity.setSupplier(supplier);
                supplierRateEntity.setUser(user);

                supplierRateRepository.save(supplierRateEntity);
            }
    }
}

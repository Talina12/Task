
package com.food4good.facad;

import com.food4good.database.entities.Supplier;
import com.food4good.database.entities.SupplierRate;
import com.food4good.database.entities.User;
import com.food4good.database.repositories.SupplierRateRepository;
import com.food4good.database.repositories.SupplierRepository;
import com.food4good.database.repositories.UsersRepository;
import com.food4good.dto.SupplierDTO;
import com.food4good.dto.SupplierInfoDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    SupplierRepository supplierRepository;
    SupplierRateRepository supplierRateRepository;
    UsersRepository usersRepository;

    public SupplierService(SupplierRepository supplierRepository, SupplierRateRepository suppliersRateRepository, UsersRepository usersRepository) {
        this.supplierRepository = supplierRepository;
        this.usersRepository = usersRepository;
        this.supplierRateRepository = suppliersRateRepository;
    }

    public SupplierDTO getById(Long supplierId) throws Exception {
        Supplier supplierEntity = supplierRepository.findById(supplierId).orElseThrow(() -> new Exception("supplier not found"));
        SupplierDTO supplierDTO = SupplierDTO.convertFromEntity(supplierEntity);
        return supplierDTO;
    }

    public List<SupplierInfoDTO> getAllInfo() {
        List<Supplier> all = supplierRepository.findAll();
        List<SupplierInfoDTO> supplierInfoDTOS = all.stream().map(SupplierInfoDTO::convertFromEntity).collect(Collectors.toList());
        return supplierInfoDTOS;
    }

    public int getAllSupplierRate(Long supplierId) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            List<SupplierRate> list = supplierRateRepository.findAllBySupplier(supplier);
            return list.size();
        }
        return 0;
    }

    public int addSupplierRate(Long supplierId, Long userId) throws Exception {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new EntityNotFoundException("supplier not found"));
        User user = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));

        Optional<SupplierRate> supplierRate = supplierRateRepository.findByUserAndSupplier(user, supplier);
        if (!supplierRate.isPresent()) {
            SupplierRate supplierRateEntity = new SupplierRate();
            supplierRateEntity.setSupplier(supplier);
            supplierRateEntity.setUser(user);

            supplierRateRepository.save(supplierRateEntity);
        }
        int finalSupplierRate = supplierRateRepository.countBySupplier(supplier);
        supplier.setRates(String.valueOf(finalSupplierRate));
        supplierRepository.save(supplier);
        return finalSupplierRate;
    }

    public int deleteSupplierRate(Long supplierId, Long userId) throws Exception {
        User user = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new EntityNotFoundException("supplier not found"));
        SupplierRate supplierRate = supplierRateRepository.findByUserAndSupplier(user, supplier).orElseThrow(() -> new EntityNotFoundException("rate by user and supplier not found"));
        supplierRateRepository.delete(supplierRate);
        int finalSupplierRate = supplierRateRepository.countBySupplier(supplier);
        supplier.setRates(String.valueOf(finalSupplierRate));
        supplierRepository.save(supplier);
        return finalSupplierRate;
    }
    
   
}

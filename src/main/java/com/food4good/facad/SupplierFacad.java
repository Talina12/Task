package com.food4good.facad;

import com.food4good.database.entities.Supplier;
import com.food4good.database.entities.SupplierRate;
import com.food4good.database.entities.User;
import com.food4good.database.repositories.SupplierRateRepository;
import com.food4good.database.repositories.SupplierRepository;
import com.food4good.database.repositories.UsersRepository;
import com.food4good.dto.SupplierDTO;
import com.food4good.dto.SupplierInfoDTO;
import com.food4good.dto.SupplierInfoDTO;

import org.springframework.data.domain.Example;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

@Service
public class SupplierFacad {
    SupplierRepository supplierRepository;
    SupplierRateRepository supplierRateRepository;
    UsersRepository userRepository;

    public SupplierFacad(SupplierRepository supplierRepository,UsersRepository userRepository,
    		SupplierRateRepository supplierRateRepository) {
        this.supplierRepository = supplierRepository;
        this.userRepository=userRepository;
        this.supplierRateRepository=supplierRateRepository;
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

	public Long deleteRate(Long userId,  Long supplierId)throws Exception {
		//get user by user id
		Optional<User> optionalUser=userRepository.findById(userId);
		User user = optionalUser.orElseThrow(()->new Exception("user not found"));
		//get supplier by supplier id
		Optional<Supplier> optionalSupplier=supplierRepository.findById(supplierId);
		Supplier supplier = optionalSupplier.orElseThrow(()->new Exception("user not found"));
		// get supplier rate by user and supplier
		Optional<SupplierRate> optionalSuplierRate =supplierRateRepository.findByUserandSupplier(user, supplier);
		SupplierRate supplierRate = optionalSuplierRate.orElseThrow(()->new Exception("user not found"));
		supplierRateRepository.delete(supplierRate);
		return supplierRateRepository.countBySupplier(supplier);
	}
    
    
}

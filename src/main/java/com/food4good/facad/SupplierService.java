
package com.food4good.facad;

import com.food4good.database.entities.Supplier;
import com.food4good.database.entities.SupplierRate;
import com.food4good.database.entities.User;
import com.food4good.database.repositories.SupplierRateRepository;
import com.food4good.database.repositories.SupplierRepository;
import com.food4good.database.repositories.UsersRepository;
import com.food4good.dto.DestinationRequest;
import com.food4good.dto.SupplierByUserDTO;
import com.food4good.dto.SupplierDTO;
import com.food4good.dto.SupplierInfoDTO;
import com.food4good.dto.geocoding.GeoPoint;
import com.food4good.facad.SupplierByUser;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    SupplierRepository supplierRepository;
    SupplierRateRepository supplierRateRepository;
    UsersRepository usersRepository;
    AddressService addressService;

    public SupplierService(SupplierRepository supplierRepository, SupplierRateRepository suppliersRateRepository, UsersRepository usersRepository, AddressService addressService) {
        this.supplierRepository = supplierRepository;
        this.usersRepository = usersRepository;
        this.supplierRateRepository = suppliersRateRepository;
        this.addressService = addressService;
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

	public List<SupplierByUserDTO> getAllInfo(User user, GeoPoint possition) throws Exception {
		List<Supplier> allSuppliers = supplierRepository.findAll();
		ArrayList <SupplierByUser> suppliersByUser = new ArrayList <SupplierByUser>();
		allSuppliers.forEach(s->suppliersByUser.add(new SupplierByUser(s)));
		if (possition != null)   setSupliersDistance(suppliersByUser,possition);
		suppliersByUser.forEach(s->s.setFavorite(isFavorite(user,s.getSupplier())));
		return suppliersByUser.stream().map(SupplierByUserDTO::convertFromEntity).collect(Collectors.toList());
	}
    
    public boolean isFavorite(User user, Supplier supplier) {
    	if (supplierRateRepository.findByUserAndSupplier(user, supplier).isPresent())
    		return true;
    	else
    	    return false;
		}
    
    public ArrayList<SupplierByUser> setSupliersDistance(ArrayList<SupplierByUser> suppliersByUser, GeoPoint possition) throws Exception{
    	ArrayList <Long> suppliersId = new ArrayList <Long>();
    	suppliersByUser.forEach(s->suppliersId.add(s.getSupplier().getId()));
    	HashMap <Long,String> allDestinations = addressService.getDestination(new DestinationRequest(possition,suppliersId));
    	suppliersByUser.forEach(s->s.setDistance(allDestinations.get(s.getSupplier().getId())));
    	return suppliersByUser;
    }

	
}

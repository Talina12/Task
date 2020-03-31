
package com.food4good.facad;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.food4good.database.entities.Products;
import com.food4good.database.entities.Supplier;
import com.food4good.database.entities.SupplierRate;
import com.food4good.database.entities.User;
import com.food4good.database.repositories.ProductsRepository;
import com.food4good.database.repositories.SupplierRateRepository;
import com.food4good.database.repositories.SupplierRepository;
import com.food4good.database.repositories.UsersRepository;
import com.food4good.dto.*;
import com.food4good.dto.geocoding.GeoPoint;

import org.springframework.http.ResponseEntity;
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
    ProductsRepository productsRepository;

    public SupplierService(SupplierRepository supplierRepository, SupplierRateRepository suppliersRateRepository, UsersRepository usersRepository, AddressService addressService,ProductsRepository productsRepository) {
        this.supplierRepository = supplierRepository;
        this.usersRepository = usersRepository;
        this.supplierRateRepository = suppliersRateRepository;
        this.addressService = addressService;
        this.productsRepository=productsRepository;
    }

    public SupplierDTO getById(Long supplierId) throws Exception {
        Supplier supplierEntity = supplierRepository.findById(supplierId).orElseThrow(() -> new Exception("supplier not found"));
        SupplierDTO supplierDTO = SupplierDTO.convertFromEntity(supplierEntity);
        return supplierDTO;
    }

    public List<SupplierInfoDTO> getAllInfo() throws Exception{
        List<Supplier> all = supplierRepository.findAll();
        List<SupplierInfoDTO> supplierInfoDTOS = new ArrayList<>();
        for (Supplier supplier : all) {
            SupplierInfoDTO supplierInfoDTO = SupplierInfoDTO.convertFromEntity(supplier);
            supplierInfoDTOS.add(supplierInfoDTO);
        }
        return supplierInfoDTOS;
    }
    public List<SupplierByUserDTO> getAllInfo(User user, GeoPoint possition) throws Exception {
        List<Supplier> allSuppliers = supplierRepository.findAll();
        ArrayList <SupplierByUser> suppliersByUser = new ArrayList <SupplierByUser>();
        allSuppliers.forEach(s->suppliersByUser.add(new SupplierByUser(s)));
        if (possition != null)   setSupliersDistance(suppliersByUser,possition);
        suppliersByUser.forEach(s->s.setFavorite(isFavorite(user,s.getSupplier())));
        List<SupplierByUserDTO> list = new ArrayList<>();
        for (SupplierByUser supplierByUser : suppliersByUser) {
            SupplierByUserDTO supplierByUserDTO = SupplierByUserDTO.convertFromEntity(supplierByUser);
            list.add(supplierByUserDTO);
        }
        return list;
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


    public SupplierInfoDTO createSupplier(SupplierInfoDTO supplierInfoDTO) throws JsonProcessingException {
        Supplier supplier = SupplierInfoDTO.convertToEntity(supplierInfoDTO);
        supplier = supplierRepository.save(supplier);
        supplierInfoDTO.getSupplierDTO().setId(supplier.getId());
        for(ProductDTO productDTO : supplierInfoDTO.getProductDTOList())
        {
            Products product=ProductDTO.convertToEntity(productDTO,supplier);
            product= productsRepository.save(product);
            productDTO.setId(product.getId());
        }
        return supplierInfoDTO;
    }

	public List<SupplierInfoDTO> getActiveInfo() throws Exception {
		List<Supplier> all = supplierRepository.findByIsActive(true);
        List<SupplierInfoDTO> supplierInfoDTOS = new ArrayList<>();
        for (Supplier supplier : all) {
            SupplierInfoDTO supplierInfoDTO = SupplierInfoDTO.convertFromEntity(supplier);
            supplierInfoDTOS.add(supplierInfoDTO);
        }
        return supplierInfoDTOS;
	}
}

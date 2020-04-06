package com.food4good.dto;

import com.food4good.database.entities.Supplier;
import com.food4good.database.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class UsersDTO {

    @NotNull
    private String name;
    private String phoneNumber;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String roles;
    private Map<String, Boolean> preferences;
    private SupplierDTO supplier;
    @NotNull
    private long supplierId;

    public static UsersDTO convertFromEntity(User user) {
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setEmail(user.getEmail());
        usersDTO.setName(user.getName());
        usersDTO.setPassword(user.getPassword());
        usersDTO.setPhoneNumber(user.getPhoneNumber());
        usersDTO.setRoles(user.getRoles());
        usersDTO.setSupplier(SupplierDTO.convertFromEntity(user.getSupplier()));
        return usersDTO;
    }

    public static User convertToEntity(UsersDTO usersDTO, Supplier supplier) {
        User user=new User();
        user.setPhoneNumber(usersDTO.getPhoneNumber());
        user.setRoles(usersDTO.getRoles());
        user.setEmail(usersDTO.getEmail());
        user.setName(usersDTO.getName());
        user.setPassword(usersDTO.getPassword());
        user.setSupplier(supplier);
        String uuid = String.valueOf(UUID.randomUUID());
        user.setToken(uuid);
        user.setUdid(uuid);
        return user;

    }
}

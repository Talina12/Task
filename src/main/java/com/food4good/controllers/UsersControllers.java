package com.food4good.controllers;

import com.food4good.database.entities.User;
import com.food4good.dto.AdminRegisterRequestDTO;
import com.food4good.dto.LoginResponseDTO;
import com.food4good.dto.UsersDTO;
import com.food4good.facad.PushNotificationService;
import com.food4good.facad.UsersService;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/superAdmin/users")
@CrossOrigin
public class UsersControllers {
    private final UsersService usersService;
    private PushNotificationService pushNotificationService;
    

    public UsersControllers(UsersService usersService, PushNotificationService pushNotificationService) {
        this.usersService = usersService;
        this.pushNotificationService = pushNotificationService;
    }

    @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    public User userById(@PathVariable("userId") Long userId) throws Exception {
        return usersService.getById(userId);
    }
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<UsersDTO> getAllUsers() throws Exception {
        return usersService.getAll();
    }
    @PostMapping(value= "/registration")
    public ResponseEntity<LoginResponseDTO> registerAdmin(@RequestBody @NonNull AdminRegisterRequestDTO adminReqestDTO) throws Exception {
        return ResponseEntity.ok(usersService.registerAdmin(adminReqestDTO));
    }
}

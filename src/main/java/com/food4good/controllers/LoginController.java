package com.food4good.controllers;

import com.food4good.config.GlobalProperties;
import com.food4good.dto.AdminRegisterRequestDTO;
import com.food4good.dto.LoginReqestDTO;
import com.food4good.dto.LoginResponseDTO;
import com.food4good.dto.AdminRequestDTO;
import com.food4good.facad.UsersService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    private final UsersService usersService;
    @Autowired
    GlobalProperties globalProperties;
    
    public LoginController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping(value = "/user")
    public LoginResponseDTO loginUser(@RequestBody LoginReqestDTO loginReqestDTO) throws Exception {
        return usersService.loginUser(loginReqestDTO);
    }
    
    @PostMapping(value= "/admin_registration")
    @CrossOrigin
    public ResponseEntity<LoginResponseDTO> registerAdmin(@RequestHeader("Authorization") String superToken, @RequestBody AdminRegisterRequestDTO adminReqestDTO) {
    	if (!superToken.equals(globalProperties.getSuperAdminToken())) {
    		log.debug("the token is not a superAdminToken");
    		return  ResponseEntity.badRequest().build();
    		}
    	else
    		return ResponseEntity.ok(usersService.registerAdmin(adminReqestDTO));
    }
    
    @PostMapping(value= "/superAdmin")
    @CrossOrigin
    public ResponseEntity<LoginResponseDTO> loginSuperAdmin(@RequestBody @NonNull AdminRequestDTO superAdminRequest){
    	return ResponseEntity.ok(usersService.loginAdmin(superAdminRequest,"SUPER_ADMIN"));
    }
    
    @PostMapping("/admin")
    public ResponseEntity<LoginResponseDTO> loginAdmin(@RequestBody @NonNull AdminRequestDTO adminRequest){
    	return ResponseEntity.ok(usersService.loginAdmin(adminRequest, "ADMIN"));
    }
    
    @PostMapping("/test")
    public String test(){ return "Test = ok";}
}

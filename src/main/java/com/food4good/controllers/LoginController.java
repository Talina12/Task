package com.food4good.controllers;

import com.food4good.dto.CoordinatesRequest;
import com.food4good.dto.LoginReqestDTO;
import com.food4good.dto.LoginResponseDTO;
import com.food4good.facad.UsersService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UsersService usersService;
    public LoginController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping(value = "/user")
    public LoginResponseDTO loginUser(@RequestBody LoginReqestDTO loginReqestDTO) throws Exception {
        return usersService.loginUser(loginReqestDTO);
    }
}

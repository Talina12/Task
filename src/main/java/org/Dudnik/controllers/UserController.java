package org.Dudnik.controllers;

import org.Dudnik.dto.LoginDTO;
import org.Dudnik.dto.RegisterDTO;
import org.Dudnik.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path="/registration")
    public ResponseEntity<String> addUser (@Valid @RequestBody RegisterDTO data) {
         return ResponseEntity.ok(userService.addUser(data));
    }
    @PostMapping(path ="/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginDTO loginData){
        return ResponseEntity.ok(userService.loginUser(loginData));
    }

    //the best way to chek Authorization header in Authorization filter
    @GetMapping(path = "/get_name")
    public ResponseEntity<String> getUserName(@RequestHeader("Authorization")String token){
        return ResponseEntity.ok(userService.getUserName(token));
    }
}

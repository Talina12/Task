package org.Dudnik.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.Dudnik.dto.LoginDTO;
import org.Dudnik.dto.RegisterDTO;
import org.Dudnik.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/user")

public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(path="/registration")
    public ResponseEntity<String> addUser (@NonNull @Valid @RequestBody RegisterDTO data) {
         return ResponseEntity.ok(userService.addUser(data));
    }
    @PostMapping(path ="/login")
    public ResponseEntity<String> loginUser(@NonNull @Valid @RequestBody LoginDTO loginData){
        return ResponseEntity.ok(userService.loginUser(loginData));
    }


    @GetMapping(path = "/get_name")
    public ResponseEntity<String> getUserName(@RequestAttribute("decodedToken") DecodedJWT token){
        return ResponseEntity.ok(userService.getUserName(token.getSubject()));
    }
}

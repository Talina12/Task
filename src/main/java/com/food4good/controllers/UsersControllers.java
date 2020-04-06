package com.food4good.controllers;

import com.food4good.database.entities.User;
import com.food4good.dto.UsersDTO;
import com.food4good.facad.UsersService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/superAdmin/users")
public class UsersControllers {
    private final UsersService usersService;
    

    public UsersControllers(UsersService usersService) {
        this.usersService = usersService;
        
    }

    @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    public User userById(@PathVariable("userId") Long userId) throws Exception {
        return usersService.getById(userId);
    }
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<UsersDTO> getAllUsers() throws Exception {
        return usersService.getAll();
    }
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@Validated @RequestBody UsersDTO usersDTO) throws Exception{
       usersService.createUser(usersDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

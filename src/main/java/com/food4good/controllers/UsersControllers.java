package com.food4good.controllers;

import com.food4good.database.entities.User;
import com.food4good.facad.UsersService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
public class UsersControllers {
    private final UsersService usersService;

    public UsersControllers(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    public User userById(@PathVariable("userId") Long userId) throws Exception {
        return usersService.getById(userId);
    }
}

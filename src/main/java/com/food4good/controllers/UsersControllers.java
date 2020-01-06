package com.food4good.controllers;

import com.food4good.database.entities.User;
import com.food4good.facad.UsersFacad;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
public class UsersControllers {
    private final UsersFacad users;

    public UsersControllers(UsersFacad users) {
        this.users = users;
    }

    @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    public User userById(@PathVariable("userId") Long userId) throws Exception {
        return users.getById(userId);
    }
}

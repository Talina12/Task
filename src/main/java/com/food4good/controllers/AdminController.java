package com.food4good.controllers;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food4good.dto.NotificationDTO;
import com.food4good.facad.UsersService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private final UsersService usersService;
	
	public AdminController(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@PostMapping("/notify")
    public ResponseEntity<Integer> sendNotifications(@RequestBody @NotNull List<NotificationDTO> notifications) {
    	return ResponseEntity.ok(usersService.sendNotifications(notifications));
    }
}

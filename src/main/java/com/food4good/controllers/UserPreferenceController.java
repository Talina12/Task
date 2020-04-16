package com.food4good.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food4good.facad.PushNotificationService;
import com.food4good.facad.UsersService;

@RestController
@RequestMapping("/user-preference")
public class UserPreferenceController {
	UsersService userService;
	PushNotificationService pushNotificationService;
	
	public UserPreferenceController(UsersService userService, PushNotificationService pushNotificationService) {
		this.userService = userService;
		this.pushNotificationService = pushNotificationService;
	}
	
	@PostMapping("/unsubscribe")
    public void unsubscribeUser() throws Exception {
    	pushNotificationService.unsubscribeSingleUser(userService.getByToken());
    }
	
	@PostMapping("/subscribe")
    public void subscribeUser() throws Exception {
		pushNotificationService.subscribeSingleUser(userService.getByToken());
	}
}

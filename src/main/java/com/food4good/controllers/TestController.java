package com.food4good.controllers;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food4good.database.entities.User;
import com.food4good.database.repositories.UsersRepository;
import com.food4good.dto.notifications.AppInstanceInfoDTO;
import com.food4good.dto.notifications.NotificationDTO;
import com.food4good.dto.notifications.SubscribeResultsDTO;
import com.food4good.facad.PushNotificationService;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private PushNotificationService pushNotificationService;
	@Autowired
	private UsersRepository usersRepository;
	
	@PostMapping("/subscribe")
	public SubscribeResultsDTO subscribeUsers() throws Exception {
		ArrayList <User> usersToSubscribe = new ArrayList<User>();
		usersToSubscribe.add(usersRepository.findById((long) 20).get());
		usersToSubscribe.add(usersRepository.findById((long) 3).get());
		return pushNotificationService.subscribeUsers(usersToSubscribe, "/topics/test2", true);
	}
	
	@PostMapping("/unsubscribe")
	public SubscribeResultsDTO unsubscribeUsers()throws Exception {
		ArrayList <User> usersToSubscribe = new ArrayList<User>();
		usersToSubscribe.add(usersRepository.findById((long) 20).get());
		usersToSubscribe.add(usersRepository.findById((long) 3).get());
		return pushNotificationService.subscribeUsers(usersToSubscribe, "/topics/test2", false);
	}
	
	@PostMapping("/send")
	public void sendNotification(@RequestBody List<NotificationDTO> list) {
		pushNotificationService.sendNotifications(list);
	}
	
	@GetMapping("/getinfo")
	public Hashtable<String, Object> getInstanceInfo() {
		User user = usersRepository.findById((long) 20).get();
		return pushNotificationService.getUserTopics(user);
	}
}

package com.food4good.facad;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.food4good.config.NotificationsConfig;
import com.food4good.database.entities.Preference;
import com.food4good.database.entities.Supplier;
import com.food4good.database.entities.User;
import com.food4good.database.entities.UsersPreference;
import com.food4good.database.repositories.PreferenceRepository;
import com.food4good.database.repositories.UserPreferenceRepository;
import com.food4good.dto.SubscribeUsersDTO;
import com.food4good.dto.notifications.AppInstanceInfoDTO;
import com.food4good.dto.notifications.Error;
import com.food4good.dto.notifications.NotificationDTO;
import com.food4good.dto.notifications.NotificationRequestDTO;
import com.food4good.dto.notifications.SubscribeResultsDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PushNotificationService {
  
	private WebClient client;
    private NotificationsConfig notifConfig;
    private UserPreferenceRepository userPreferenceRepository;
  //  private PreferenceRepository preferenceRepository;
    private UsersService usersService;
    private Preference preference;
    
    public PushNotificationService(NotificationsConfig notificationsConfig, UserPreferenceRepository userPreferenceRepository, 
    		                       PreferenceRepository preferenceRepository, UsersService usersService) throws Exception {
    	this.client = WebClient
				  .builder()
				  .build();
    	this.notifConfig = notificationsConfig;
    	this.userPreferenceRepository = userPreferenceRepository;
  //  	this.preferenceRepository = preferenceRepository;
    	this.usersService = usersService;
    	//this.preference = preferenceRepository.findById(notifConfig.getPreferenceId()).orElseThrow(() -> new Exception("Push notification preference not found"));
    }
    
	public void sendNotifications(List<NotificationDTO> notificationList) {
		WebClient.RequestBodySpec request;
		request = client.post().uri(notifConfig.getUrl()).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				               .header(HttpHeaders.AUTHORIZATION, notifConfig.getKey());
		notificationList.forEach(n->send(n,request));
	}
	
	private void send(NotificationDTO notification, WebClient.RequestBodySpec request) {
		NotificationRequestDTO requestDTO = new NotificationRequestDTO(notification);
		requestDTO.setPriority("normal");
		requestDTO.getNotification().setIcon("myicon");
	    request.bodyValue(requestDTO).retrieve().bodyToMono(Object.class).block();
	}
	
	public SubscribeResultsDTO subscribeUsers(List <User> usersToSubscribe, String topicName, boolean isSubscribe) throws Exception {
		if ((usersToSubscribe == null) || (usersToSubscribe.size() == 0)) {
			log.debug("error in subscribeUsers(List <User> usersToSubscribe, String topicName) null or empty argument");
			throw new Exception("there is no users in usersToSubscribe");}
		topicName = notifConfig.getTopicsString().concat(topicName);
		String requestUrl = (isSubscribe) ? notifConfig.getSubscribeUrl() : notifConfig.getUnsubscribeUrl();
		WebClient.RequestBodySpec request;
		request = client.post().uri(requestUrl).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				         .header(HttpHeaders.AUTHORIZATION, notifConfig.getKey());
		SubscribeUsersDTO subscribeUsersDTO = new SubscribeUsersDTO(usersToSubscribe, topicName);
		SubscribeResultsDTO requestResults =  request.bodyValue(subscribeUsersDTO).retrieve().bodyToMono(SubscribeResultsDTO.class).block();
		reportResults(requestResults.getResults());
		return requestResults;
	}

	private void reportResults(List<Error> results ) {
		int successful = 0;
		int notFound = 0;
		int invalidArgument = 0;
		int internal = 0;
		int tooManyTopics = 0;
		for (Error error :results) {
			String stringError = error.getError();
			if (stringError == null) successful++;
			else 
				if (stringError.equals("NOT_FOUND")) notFound++;
				else 
					if (stringError.equals("INVALID_ARGUMENT")) invalidArgument++;
					else 
						if (stringError.equals("INTERNAL")) internal++;
						else 
							if (stringError.equals("TOO_MANY_TOPICS")) tooManyTopics++;
			
		}
		log.info("successful = " + successful);
		log.info("NOT_FOUND = "+ notFound);
		log.info("INVALID_ARGUMENT = " + invalidArgument );
		log.info("INTERNAL = " + internal);
		log.info("TOO_MANY_TOPICS = " + tooManyTopics);
	}
	
	public Hashtable<String, Object> getUserTopics(User user) {
		String requestUrl = notifConfig.getInstanceInfo() + (user.getToken());
		AppInstanceInfoDTO appInstanceInfoDTO = client.get().uri(requestUrl+ "?details=true").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				         .header(HttpHeaders.AUTHORIZATION, notifConfig.getKey()).retrieve().bodyToMono(AppInstanceInfoDTO.class).block();
		if (appInstanceInfoDTO.getRel() == null) return null;
		else return appInstanceInfoDTO.getRel().getTopics();
	}

	public void unsubscribeSingleUser(User user) throws Exception {
		saveUsersChoice(user, false);
		Hashtable<String, Object> usersTopics = getUserTopics(user);
		if (usersTopics == null) return;
		ArrayList<User> usersToUnsubscribe = new ArrayList<User>();
		usersToUnsubscribe.add(user);
		log.info(usersTopics.toString());
		Set<String> topics = usersTopics.keySet();
		for (String topic : topics)
			subscribeUsers(usersToUnsubscribe, notifConfig.getTopicsString().concat(topic), false);
	}

	private void saveUsersChoice(User user, boolean choice) {

		UsersPreference usersPreference;
		Optional<UsersPreference> optionalUsersPreference = userPreferenceRepository.findByUserAndPreference(user, preference);
		preference.setSendPush(false);
		if (optionalUsersPreference.isPresent()) {
	    	usersPreference = optionalUsersPreference.get();
	    	usersPreference.setPreference(preference);
	    }
	    else {
	    	usersPreference = new UsersPreference();
	    	usersPreference.setPreference(preference);
	    	usersPreference.setUser(user);
	    }
		userPreferenceRepository.save(usersPreference);
	}

	public void subscribeSingleUser(User user) {
		List<Supplier> suppliersToSubscribe = usersService.getFavoriteSuppliers(user);
		List<String> topics = suppliersToSubscribe.stream().map((s) -> getSupplierTopic(s)).collect(Collectors.toList());
		//client.post().uri(notifConfig.getSubscribeSingleUserUrl())
	}
	
	private String getSupplierTopic(Supplier supplier) {
		return notifConfig.getTopicsName().concat(supplier.getId().toString());
	}
}

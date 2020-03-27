package com.food4good.facad;

import com.food4good.config.BadRequestException;
import com.food4good.config.NotificationsConfig;
import com.food4good.database.entities.Supplier;
import com.food4good.database.entities.User;
import com.food4good.database.repositories.SupplierRepository;
import com.food4good.database.repositories.UsersRepository;
import com.food4good.dto.AdminRegisterRequestDTO;
import com.food4good.dto.LoginReqestDTO;
import com.food4good.dto.LoginResponseDTO;
import com.food4good.dto.NotificationDTO;
import com.food4good.dto.NotificationRequestDTO;
import com.food4good.dto.AdminRequestDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
    UsersRepository usersRepository;
    SupplierRepository supplierRepository;
    private WebClient client;
    private NotificationsConfig notifConfig;
    
    public UsersService(UsersRepository usersRepository, SupplierRepository supplierRepository, NotificationsConfig notifConfig) {
        this.usersRepository = usersRepository;
        this.supplierRepository = supplierRepository;
        this.notifConfig = notifConfig;
        this.client = WebClient
				  .builder()
				  .baseUrl(notifConfig.getUrl())
				  .build();
    }

    public User getById(Long userId)  {
    	return usersRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("user not found"));
    }
    
    public User getByToken() throws Exception{
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user==null)
        {
            throw new  EntityNotFoundException("Authentication not found");
        }
    //	User userEntity=usersRepository.findByToken(userPrincipal.getToken()).orElseThrow(()->new EntityNotFoundException("user not found"));
    	return user;
    }

    public LoginResponseDTO loginUser(LoginReqestDTO loginReqestDTO) {
        LoginResponseDTO loginResponseDTO=new LoginResponseDTO();
        User user;
        Optional<User> optionalUser = usersRepository.findByTokenAndRoles(loginReqestDTO.getToken(), "USER");
        if(optionalUser.isPresent())
        {
            user=optionalUser.get();
        }
        else
        {
            User userToSave =new User();
            userToSave.setRoles("USER");
            userToSave.setToken(loginReqestDTO.getToken());
            userToSave.setUdid(loginReqestDTO.getUdid());
            user=usersRepository.save(userToSave);
        }
        loginResponseDTO.setToken(user.getToken());
        loginResponseDTO.setUserId(user.getId().toString());
        return loginResponseDTO;
    }

	public LoginResponseDTO registerAdmin(AdminRegisterRequestDTO adminReqestDTO) {
		User user;
		Supplier supplier = supplierRepository.findById(adminReqestDTO.getSuplierId()).orElseThrow(() -> new EntityNotFoundException(" supplier not found"));
		Optional<User> optionalUser = usersRepository.findByEmailAndRoles(adminReqestDTO.getEmail(), "ADMIN");
		if (optionalUser.isPresent()) throw new BadRequestException("a user with such an email exists");
		else user=saveUser(adminReqestDTO, supplier);
		return new LoginResponseDTO(user);
	}

	public LoginResponseDTO loginAdmin(AdminRequestDTO superAdminRequest, String role) {
		User user = usersRepository.findByEmailAndPasswordAndRoles(superAdminRequest.getEmail(), superAdminRequest.getPassword(), role)
				.orElseThrow(() -> new EntityNotFoundException(" such admin not found"));
		return new LoginResponseDTO(user); 
	}
	
	protected User saveUser(AdminRegisterRequestDTO adminReqestDTO, Supplier supplier) {
		User userToSave = new User();
		userToSave.setEmail(adminReqestDTO.getEmail());
		userToSave.setName(adminReqestDTO.getName());
		userToSave.setPassword(adminReqestDTO.getPassword());
		userToSave.setPhone_number(adminReqestDTO.getPhone());
		userToSave.setRoles("ADMIN");
		String uuid = String.valueOf(UUID.randomUUID());
		userToSave.setToken(uuid);
		userToSave.setUdid(uuid);
		userToSave.setSupplier(supplier);
		return usersRepository.save(userToSave);
	}

	public void sendNotifications(List<NotificationDTO> notificationList) {
		WebClient.RequestBodySpec request;
		request = client.post().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				               .header(HttpHeaders.AUTHORIZATION, notifConfig.getKey());
		notificationList.forEach(n->send(n,request));
	}
	
	private void send(NotificationDTO notification, WebClient.RequestBodySpec request) {
		NotificationRequestDTO requestDTO = new NotificationRequestDTO(notification);
		requestDTO.setPriority("normal");
		requestDTO.getNotification().setIcon("myicon");
	    request.bodyValue(requestDTO).retrieve().bodyToMono(Object.class).block();
	}
}

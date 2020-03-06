package com.food4good.facad;

import com.food4good.database.entities.User;
import com.food4good.database.repositories.UsersRepository;
import com.food4good.dto.LoginReqestDTO;
import com.food4good.dto.LoginResponseDTO;
import com.food4good.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UsersService {
    UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User getById(Long userId)  {
    	return usersRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("user not found"));
    }
    
    public User getByToken() throws Exception{
        UserPrincipal userPrincipal= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userPrincipal==null)
        {
            throw new  EntityNotFoundException("Authentication not found");
        }
    	User userEntity=usersRepository.findByToken(userPrincipal.getToken()).orElseThrow(()->new EntityNotFoundException("user not found"));
    	return userEntity;
    }

    public LoginResponseDTO loginUser(LoginReqestDTO loginReqestDTO) {
        LoginResponseDTO loginResponseDTO=new LoginResponseDTO();
        User user;
        Optional<User> optionalUser = usersRepository.findByTokenAndRoles(loginReqestDTO.getUdid(), "USER");
        if(optionalUser.isPresent())
        {
            user=optionalUser.get();
        }
        else
        {
            User userToSave =new User();
            userToSave.setRoles("USER");
            userToSave.setToken(loginReqestDTO.getUdid());
            userToSave.setUdid(loginReqestDTO.getUdid());
            user=usersRepository.save(userToSave);
        }
        loginResponseDTO.setToken(user.getToken());
        loginResponseDTO.setUserId(user.getId().toString());
        return loginResponseDTO;
    }
}

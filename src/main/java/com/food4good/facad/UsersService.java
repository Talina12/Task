package com.food4good.facad;

import com.food4good.database.entities.User;
import com.food4good.database.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UsersService {
    UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User getById(Long userId)  {
    	return usersRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("user not found"));
    }
    
    public User getByToken(String token) throws Exception{
    	User userEntity=usersRepository.findByToken(token).orElseThrow(()->new Exception("user not found"));
    	return userEntity;
    }
}

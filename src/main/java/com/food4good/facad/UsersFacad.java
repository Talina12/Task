package com.food4good.facad;

import com.food4good.database.entities.Supplier;
import com.food4good.database.entities.User;
import com.food4good.database.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersFacad {
    UsersRepository usersRepository;

    public UsersFacad(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User getById(Long userId) {
        return usersRepository.findById(userId).orElseGet(null);
    }
    
    public User getByToken(String token) throws Exception{
    	User userEntity=usersRepository.findByToken(token).orElseThrow(()->new Exception("user not found"));
    	return userEntity;
    }
}

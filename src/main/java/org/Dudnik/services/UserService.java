package org.Dudnik.services;

import org.Dudnik.config.UserNotFoundException;
import org.Dudnik.database.entities.User;
import org.Dudnik.database.repositories.UserRepository;
import org.Dudnik.dto.LoginDTO;
import org.Dudnik.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

@Autowired
    UserRepository usersRepository;

    public String  addUser(RegisterDTO data){
        String name = data.getName();
        String email = data.getEmail();
        Optional<User> existingUser = usersRepository.findByEmail(email);
        if (existingUser.isPresent())
            throw new EntityExistsException("a user with such   email exists");
        else {
            User userToSave = new User(name,email, data.getPassword());
            usersRepository.save(userToSave);
        }
        return "The user created";
}

    public String loginUser(LoginDTO loginData) {
        Optional<User> existingUser = usersRepository.findByEmail(loginData.getEmail());
        User user =  existingUser.orElseThrow(() -> new UserNotFoundException(" user not found"));
        if (!user.getPassword().equals(loginData.getPassword()))
            new  UserNotFoundException(" user not found");
        return getJwt(user);
    }

    private String getJwt(User user){
        String jwt = Jwts.builder()
                .setSubject(user.getEmail()) //save in application.properties
                .setExpiration(System.currentTimeMillis() +100000000)
                .claim("name", user.getName())
                .claim("scope", "/users")
                .signWith(
                        SignatureAlgorithm.HS256,
                        "secret".getBytes("UTF-8")
                )
                .compact();
        return jwt;
    }

    private String decodeJwt(String jwt){
        //decode and return user email
    }

    private boolean validateJwt(String jwt){
       // check expire date
    }


    public String getUserName(String token) {
        if (validateJwt(token))
            Optional<User> user = usersRepository.findByEmail(validateJwt(token)).orElseThrow(() -> new UserNotFoundException(" user not found"));
        return user.getName();
    }
}

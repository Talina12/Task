package org.Dudnik.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.Dudnik.config.GlobalProperties;
import org.Dudnik.config.UserAlreadyExistException;
import org.Dudnik.config.UserNotFoundException;
import org.Dudnik.database.entities.User;
import org.Dudnik.database.repositories.UserRepository;
import org.Dudnik.dto.LoginDTO;
import org.Dudnik.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class UserService {


    private UserRepository usersRepository;
    private GlobalProperties globalProperties;
    private Algorithm algorithm;
    private JWTVerifier jwtVerifier;

    public UserService(UserRepository usersRepository, GlobalProperties globalProperties){
        algorithm = Algorithm.HMAC256(globalProperties.getSecret());
        jwtVerifier = JWT.require(algorithm).withIssuer("auth0").build();
        this.usersRepository = usersRepository;
        this.globalProperties = globalProperties;
    }

    public String  addUser(RegisterDTO data){
        String name = data.getName();
        String email = data.getEmail().toLowerCase();
        Optional<User> existingUser = usersRepository.findByEmail(email);
        if (existingUser.isPresent())
            throw new UserAlreadyExistException("a user with such   email exists");
        else {
            User userToSave = new User(name,email, data.getPassword());
            usersRepository.save(userToSave);
        }
        return "The user created";
}

    public String loginUser(LoginDTO loginData) {
        Optional<User> existingUser = usersRepository.findByEmail(loginData.getEmail().toLowerCase());
        User user =  existingUser.orElseThrow(() -> new UserNotFoundException(" user not found"));
        if (!user.getPassword().equals(loginData.getPassword()))
            new  UserNotFoundException(" user not found");
        return getJwt(user);
    }

    private String getJwt(User user){
        Calendar expireCalendar = Calendar.getInstance();
        expireCalendar.add(Calendar.HOUR, 2);
        String token = JWT.create().withSubject(user.getEmail()).withExpiresAt(expireCalendar.getTime()).withClaim("name", user.getName())
                                   .withIssuer("auth0").sign(algorithm);
        return token;
    }

    private DecodedJWT verifyJwt(String token){
        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
            return jwt;
        }
        catch (JWTVerificationException exception){
            return  null;
        }
    }


    public String getUserName(String email) {
        User user = usersRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(" user not found"));
        return user.getName();
    }
}

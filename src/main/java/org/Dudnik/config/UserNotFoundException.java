package org.Dudnik.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "User does not exist")
public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String string) {
        super(string);
    }
}
